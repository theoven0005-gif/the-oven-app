package com.example.theoven

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.tasks.await

/**
 * Firestore-backed order store. [OrderManager] is kept as the local
 * cache/fallback so the app works fully offline.
 *
 * Firestore schema (matches the admin panel):
 *   orders/{orderId} = {
 *     customerId, customerName, phone,
 *     type: "delivery" | "pickup" | "dine-in",
 *     status: String (see [toFirestoreStatus]),
 *     paymentMethod: "online" | "cod" | "cash",
 *     paymentBank, paymentScreenshotUrl,
 *     address, tableNumber,
 *     items: [{ name, quantity, price, size }],
 *     subtotal, deliveryFee, total,
 *     createdAt, updatedAt
 *   }
 *
 * NOTE on Storage: this Firebase project is on the Spark plan, where Cloud
 * Storage requires Blaze. Screenshot uploads are therefore attempted inside
 * a try/catch — when Storage is unavailable the order is saved WITHOUT a
 * screenshot URL and the app keeps working. Nothing here ever throws.
 */
object FirestoreOrderRepository {

    private const val TAG = "FirestoreOrders"
    private const val COLLECTION = "orders"

    // ---------------- status / type mapping ----------------

    /** Local enum -> Firestore string. */
    fun PaymentStatus.toFirestoreStatus(): String = when (this) {
        PaymentStatus.PENDING_VERIFICATION -> "pending_verification"
        PaymentStatus.APPROVED -> "approved"
        PaymentStatus.REJECTED -> "rejected"
        PaymentStatus.COD_CONFIRMED -> "cod_confirmed"
    }

    /**
     * Firestore string -> local enum.
     * The admin panel may use extra workflow statuses; they are folded into the
     * closest local state so the UI never crashes on an unknown value.
     */
    fun String.toPaymentStatus(): PaymentStatus = when (lowercase()) {
        "pending_verification", "pending" -> PaymentStatus.PENDING_VERIFICATION
        "approved", "confirmed", "preparing", "ready", "out_for_delivery",
        "delivered", "picked_up", "served", "completed" -> PaymentStatus.APPROVED
        "rejected", "cancelled" -> PaymentStatus.REJECTED
        "cod_confirmed", "cod", "cash_due", "cash_collected" -> PaymentStatus.COD_CONFIRMED
        else -> PaymentStatus.PENDING_VERIFICATION
    }

    private fun String.normalizeOrderType(): String = when {
        contains("pick", ignoreCase = true) -> "pickup"
        contains("dine", ignoreCase = true) -> "dine-in"
        else -> "delivery"
    }

    private fun PaymentMethod.toFirestoreMethod(): String = when (this) {
        PaymentMethod.BANK_TRANSFER -> "online"
        PaymentMethod.CASH_ON_DELIVERY -> "cod"
    }

    // ---------------- create ----------------

    /**
     * Writes the order document to Firestore. The local [OrderModel.orderId] is
     * used as the Firestore document ID so the app and the admin panel agree.
     *
     * Screenshot upload is best-effort: on the Spark plan Storage is
     * unavailable, so any upload failure is swallowed and the order is saved
     * without a paymentScreenshotUrl.
     *
     * Returns Result.success(documentId) or Result.failure(exception).
     * Never throws.
     */
    suspend fun createOrder(order: OrderModel): Result<String> = runCatching {
        val uid = requireNotNull(FirebaseHelper.currentUid) {
            "User must be signed in to place an order."
        }

        // 1) Best-effort screenshot upload (gracefully skipped when Storage
        //    is unavailable, e.g. Spark plan without Blaze).
        val screenshotUrl: String? = try {
            order.screenshotUri
                ?.takeIf { it.isNotBlank() }
                ?.let { uploadScreenshot(order.orderId, Uri.parse(it)) }
        } catch (e: Exception) {
            Log.w(TAG, "Screenshot upload skipped: ${e.message}")
            null
        }

        // 2) Aggregate cart lines by (name + details); CartItem already carries
        //    unitPrice + quantity after the app refactor.
        val itemsPayload = order.items
            .groupBy { it.name to it.details }
            .map { (key, lines) ->
                val (name, details) = key
                mapOf(
                    "name" to name,
                    "quantity" to lines.sumOf { it.quantity },
                    "price" to lines.first().unitPrice,
                    "size" to details
                )
            }

        val subtotal = (order.totalAmount - order.deliveryFee).coerceAtLeast(0)

        val payload = hashMapOf<String, Any?>(
            "customerId" to uid,
            "customerName" to order.customerName,
            "phone" to order.customerPhone,
            "type" to order.orderType.normalizeOrderType(),
            "status" to order.status.toFirestoreStatus(),
            "paymentMethod" to order.paymentMethod.toFirestoreMethod(),
            "paymentBank" to order.selectedBankName,
            "paymentScreenshotUrl" to screenshotUrl,
            "address" to order.deliveryAddress,
            "tableNumber" to null,
            "items" to itemsPayload,
            "subtotal" to subtotal,
            "deliveryFee" to order.deliveryFee,
            "total" to order.totalAmount,
            "createdAt" to FieldValue.serverTimestamp(),
            "updatedAt" to FieldValue.serverTimestamp()
        )

        FirebaseHelper.firestore.collection(COLLECTION)
            .document(order.orderId)
            .set(payload)
            .await()

        order.orderId
    }

    /**
     * Uploads a payment screenshot to Firebase Storage.
     * @throws Exception when Storage is unavailable (e.g. Spark plan) — callers
     * must catch and continue without a screenshot URL.
     */
    private suspend fun uploadScreenshot(orderId: String, localUri: Uri): String {
        val ref = FirebaseHelper.storage.reference
            .child("paymentScreenshots/$orderId.jpg")
        ref.putFile(localUri).await()
        return ref.downloadUrl.await().toString()
    }

    // ---------------- read (realtime) ----------------

    /**
     * Realtime listener for the signed-in user's orders, newest first.
     * Sorting is done client-side so no Firestore composite index is needed.
     * Returns the ListenerRegistration — pass it to [stopListening] in onDestroy.
     * Never throws; errors go to [onError].
     */
    fun listenToUserOrders(
        onUpdate: (List<OrderModel>) -> Unit,
        onError: (Exception) -> Unit = {}
    ): ListenerRegistration? {
        val uid = FirebaseHelper.currentUid ?: run {
            onError(IllegalStateException("Not signed in"))
            return null
        }
        return try {
            FirebaseHelper.firestore.collection(COLLECTION)
                .whereEqualTo("customerId", uid)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        onError(error)
                        return@addSnapshotListener
                    }
                    val orders = snapshot?.documents
                        ?.mapNotNull { it.toOrderModel() }
                        .orEmpty()
                        .sortedByDescending { it.timestamp }
                    onUpdate(orders)
                }
        } catch (e: Exception) {
            onError(e)
            null
        }
    }

    fun stopListening(registration: ListenerRegistration?) {
        try {
            registration?.remove()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // ---------------- update ----------------

    /**
     * Applies a partial update to an order (refreshes updatedAt automatically).
     *
     * NOTE: the published firestore.rules allow order updates by *admins* only,
     * so this fails for regular users — kept for admin-side use.
     */
    suspend fun updateOrder(
        orderId: String,
        patch: Map<String, Any?>
    ): Result<Unit> = runCatching {
        val data = patch.toMutableMap()
        data["updatedAt"] = FieldValue.serverTimestamp()
        FirebaseHelper.firestore.collection(COLLECTION)
            .document(orderId)
            .update(data)
            .await()
    }

    /** Convenience wrapper for status changes (admin-side use). */
    suspend fun updateOrderStatus(orderId: String, status: PaymentStatus): Result<Unit> =
        updateOrder(orderId, mapOf("status" to status.toFirestoreStatus()))

    // ---------------- mapping ----------------

    @Suppress("UNCHECKED_CAST")
    private fun DocumentSnapshot.toOrderModel(): OrderModel? {
        val items = (get("items") as? List<Map<String, Any?>>).orEmpty().map { m ->
            CartItem(
                name = m["name"] as? String ?: "Item",
                details = m["size"] as? String ?: "",
                unitPrice = (m["price"] as? Number)?.toInt() ?: 0,
                quantity = (m["quantity"] as? Number)?.toInt() ?: 1
            )
        }
        val paymentMethod = when (getString("paymentMethod")) {
            "online" -> PaymentMethod.BANK_TRANSFER
            else -> PaymentMethod.CASH_ON_DELIVERY
        }
        return OrderModel(
            orderId = id,
            customerName = getString("customerName") ?: "",
            customerPhone = getString("phone") ?: "",
            deliveryAddress = getString("address") ?: "",
            orderType = when (getString("type")) {
                "pickup" -> "Pickup"
                "dine-in" -> "Dine-In"
                else -> "Delivery"
            },
            items = items,
            totalAmount = (getLong("total") ?: 0L).toInt(),
            deliveryFee = (getLong("deliveryFee") ?: 0L).toInt(),
            paymentMethod = paymentMethod,
            selectedBankName = getString("paymentBank"),
            screenshotUri = getString("paymentScreenshotUrl"),
            status = (getString("status") ?: "pending_verification").toPaymentStatus(),
            timestamp = getTimestamp("createdAt")?.toDate()?.time ?: System.currentTimeMillis()
        )
    }
}
