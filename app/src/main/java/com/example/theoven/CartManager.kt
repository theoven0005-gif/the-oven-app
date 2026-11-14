package com.example.theoven

import android.content.Context
import org.json.JSONArray

object CartManager {
    private const val PREFS = "cart_prefs"
    private const val KEY_CART = "cart_json"

    private val cartItems = mutableListOf<CartItem>()
    private var loaded = false

    private fun ensureLoaded(context: Context) {
        if (loaded) return
        loaded = true
        try {
            val json = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .getString(KEY_CART, null)
            if (!json.isNullOrEmpty()) {
                val arr = JSONArray(json)
                for (i in 0 until arr.length()) {
                    cartItems.add(CartItem.fromJson(arr.getJSONObject(i)))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            cartItems.clear()
        }
    }

    private fun persist(context: Context) {
        try {
            val arr = JSONArray()
            cartItems.forEach { arr.put(it.toJson()) }
            context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_CART, arr.toString())
                .apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addItem(context: Context, item: CartItem) {
        ensureLoaded(context)
        val existing = cartItems.indexOfFirst { it.name == item.name && it.details == item.details }
        if (existing >= 0) {
            val old = cartItems[existing]
            cartItems[existing] = old.copy(quantity = old.quantity + item.quantity)
        } else {
            cartItems.add(item)
        }
        persist(context)
    }

    fun getItems(context: Context): List<CartItem> {
        ensureLoaded(context)
        return cartItems.toList()
    }

    fun getTotalPrice(context: Context): Int {
        ensureLoaded(context)
        return cartItems.sumOf { it.totalPrice() }
    }

    fun getItemsCount(context: Context): Int {
        ensureLoaded(context)
        return cartItems.sumOf { it.quantity }
    }

    fun clearCart(context: Context) {
        ensureLoaded(context)
        cartItems.clear()
        persist(context)
    }
}
