package com.example.theoven

import org.json.JSONArray
import org.json.JSONObject

enum class PaymentMethod {
    BANK_TRANSFER,
    CASH_ON_DELIVERY
}

enum class PaymentStatus {
    PENDING_VERIFICATION,
    APPROVED,
    REJECTED,
    COD_CONFIRMED
}

data class OrderModel(
    val orderId: String,
    val customerName: String,
    val customerPhone: String,
    val deliveryAddress: String,
    val orderType: String,
    val items: List<CartItem>,
    val totalAmount: Int,
    val deliveryFee: Int,
    val paymentMethod: PaymentMethod,
    val selectedBankName: String? = null,
    val screenshotUri: String? = null,
    var status: PaymentStatus = PaymentStatus.PENDING_VERIFICATION,
    val timestamp: Long = System.currentTimeMillis()
) {
    fun toJson(): JSONObject {
        val itemsArr = JSONArray()
        items.forEach { itemsArr.put(it.toJson()) }
        return JSONObject()
            .put("orderId", orderId)
            .put("customerName", customerName)
            .put("customerPhone", customerPhone)
            .put("deliveryAddress", deliveryAddress)
            .put("orderType", orderType)
            .put("items", itemsArr)
            .put("totalAmount", totalAmount)
            .put("deliveryFee", deliveryFee)
            .put("paymentMethod", paymentMethod.name)
            .put("selectedBankName", selectedBankName ?: JSONObject.NULL)
            .put("screenshotUri", screenshotUri ?: JSONObject.NULL)
            .put("status", status.name)
            .put("timestamp", timestamp)
    }

    companion object {
        fun fromJson(o: JSONObject): OrderModel {
            val itemsList = mutableListOf<CartItem>()
            val arr = o.optJSONArray("items")
            if (arr != null) {
                for (i in 0 until arr.length()) {
                    itemsList.add(CartItem.fromJson(arr.getJSONObject(i)))
                }
            }
            return OrderModel(
                orderId = o.optString("orderId"),
                customerName = o.optString("customerName"),
                customerPhone = o.optString("customerPhone"),
                deliveryAddress = o.optString("deliveryAddress"),
                orderType = o.optString("orderType", "Delivery"),
                items = itemsList,
                totalAmount = o.optInt("totalAmount"),
                deliveryFee = o.optInt("deliveryFee"),
                paymentMethod = runCatching {
                    PaymentMethod.valueOf(o.optString("paymentMethod", "CASH_ON_DELIVERY"))
                }.getOrDefault(PaymentMethod.CASH_ON_DELIVERY),
                selectedBankName = o.optString("selectedBankName").takeIf { it.isNotEmpty() },
                screenshotUri = o.optString("screenshotUri").takeIf { it.isNotEmpty() },
                status = runCatching {
                    PaymentStatus.valueOf(o.optString("status", "PENDING_VERIFICATION"))
                }.getOrDefault(PaymentStatus.PENDING_VERIFICATION),
                timestamp = o.optLong("timestamp", System.currentTimeMillis())
            )
        }
    }
}
