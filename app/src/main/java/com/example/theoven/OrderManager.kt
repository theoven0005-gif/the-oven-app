package com.example.theoven

import android.content.Context
import org.json.JSONArray

object OrderManager {
    private const val PREFS = "orders_prefs"
    private const val KEY_ORDERS = "orders_json"

    private val orders = mutableListOf<OrderModel>()
    private var loaded = false

    private fun ensureLoaded(context: Context) {
        if (loaded) return
        loaded = true
        try {
            val json = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .getString(KEY_ORDERS, null)
            if (!json.isNullOrEmpty()) {
                val arr = JSONArray(json)
                for (i in 0 until arr.length()) {
                    orders.add(OrderModel.fromJson(arr.getJSONObject(i)))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            orders.clear()
        }
    }

    private fun persist(context: Context) {
        try {
            val arr = JSONArray()
            orders.forEach { arr.put(it.toJson()) }
            context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_ORDERS, arr.toString())
                .apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addOrder(context: Context, order: OrderModel) {
        ensureLoaded(context)
        orders.add(0, order)
        persist(context)
    }

    fun getOrders(context: Context): List<OrderModel> {
        ensureLoaded(context)
        return orders.toList()
    }

    fun getLatestOrder(context: Context): OrderModel? {
        ensureLoaded(context)
        return orders.firstOrNull()
    }

    fun getOrderById(context: Context, orderId: String): OrderModel? {
        ensureLoaded(context)
        return orders.find { it.orderId == orderId }
    }

    fun getPendingOrders(context: Context): List<OrderModel> {
        ensureLoaded(context)
        return orders.filter { it.status == PaymentStatus.PENDING_VERIFICATION }
    }

    fun updateOrderStatus(context: Context, orderId: String, newStatus: PaymentStatus): OrderModel? {
        ensureLoaded(context)
        val order = orders.find { it.orderId == orderId }
        order?.status = newStatus
        persist(context)
        return order
    }
}
