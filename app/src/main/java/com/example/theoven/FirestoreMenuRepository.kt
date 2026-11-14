package com.example.theoven

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration

/**
 * Firestore-backed menu with offline fallback to the bundled [MenuData].
 *
 * Schema: menuItems/{id} = {
 *   name, description, price, category, imageUrl, available, featured
 * }
 *
 * If Firestore is unreachable or the collection is empty, the listener emits
 * [MenuData.products] so the app keeps working exactly as before.
 * Never throws; errors go to [onError] and fall back to the local menu.
 */
object FirestoreMenuRepository {

    private const val TAG = "FirestoreMenu"
    private const val COLLECTION = "menuItems"

    data class RemoteMenuItem(
        val id: String,
        val name: String,
        val description: String,
        val price: Int,
        val category: String,
        val imageUrl: String?,
        val available: Boolean,
        val featured: Boolean
    )

    /**
     * Realtime listener for available menu items. Falls back to
     * [MenuData.products] when Firestore returns nothing (empty collection
     * or error), so the UI never goes blank.
     *
     * Returns the ListenerRegistration — pass it to [stopListening]
     * (or call remove()) in onDestroy/onStop. May return null when the
     * listener could not be attached.
     */
    fun listenToMenuItems(
        onUpdate: (List<Product>) -> Unit,
        onError: (Exception) -> Unit = {}
    ): ListenerRegistration? {
        return try {
            FirebaseHelper.firestore.collection(COLLECTION)
                .whereEqualTo("available", true)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        Log.w(TAG, "Menu listener error, using local menu", error)
                        onError(error)
                        onUpdate(MenuData.products) // graceful fallback
                        return@addSnapshotListener
                    }
                    val remote = snapshot?.documents?.mapNotNull { it.toRemoteMenuItem() }.orEmpty()
                    if (remote.isEmpty()) {
                        onUpdate(MenuData.products) // nothing seeded yet -> local menu
                    } else {
                        onUpdate(remote.map { it.toProduct() })
                    }
                }
        } catch (e: Exception) {
            Log.w(TAG, "Could not attach menu listener, using local menu", e)
            onError(e)
            onUpdate(MenuData.products)
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

    // ---------------- mapping ----------------

    private fun DocumentSnapshot.toRemoteMenuItem(): RemoteMenuItem? {
        val name = getString("name") ?: return null
        return RemoteMenuItem(
            id = id,
            name = name,
            description = getString("description") ?: "",
            price = (getLong("price") ?: 0L).toInt(),
            category = getString("category") ?: "",
            imageUrl = getString("imageUrl"),
            available = getBoolean("available") ?: true,
            featured = getBoolean("featured") ?: false
        )
    }

    /**
     * Converts a Firestore menu item to the app's [Product].
     *
     * Category names from the admin panel ("Pizza", "Burgers", "Drinks", …)
     * are matched against the bundled [MenuData.categories] with a
     * contains-based match (either direction); unknown categories fall back
     * to the first category.
     *
     * NOTE: [Product.imageRes] is a local drawable — a remote
     * [RemoteMenuItem.imageUrl] needs an image-loading library (e.g. Coil)
     * to display. Until that is added, a placeholder drawable is used.
     */
    fun RemoteMenuItem.toProduct(): Product {
        val wanted = category.trim().lowercase()
        val categoryId = MenuData.categories.firstOrNull { local ->
            val have = local.name.lowercase()
            wanted.isNotEmpty() && (have.contains(wanted) || wanted.contains(have))
        }?.id ?: MenuData.categories.first().id
        return Product(
            id = id.hashCode(),
            name = name,
            description = description.ifEmpty { category },
            price = price,
            priceLabel = "Standard",
            categoryId = categoryId,
            imageRes = R.drawable.ic_pizza_real,
            menuItemId = id.hashCode(),
            isFeatured = featured
        )
    }
}
