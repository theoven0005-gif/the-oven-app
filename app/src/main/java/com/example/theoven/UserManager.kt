package com.example.theoven

import android.content.Context
import android.content.SharedPreferences

object UserManager {
    private const val PREF_NAME = "UserPrefs"
    private const val KEY_NAME = "user_name"
    private const val KEY_PHONE = "user_phone"
    private const val KEY_EMAIL = "user_email"
    private const val KEY_ADDRESS = "user_address"
    private const val KEY_LOGGED_IN = "logged_in"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveUser(context: Context, name: String, phone: String, email: String, address: String) {
        getPrefs(context).edit().apply {
            putString(KEY_NAME, name)
            putString(KEY_PHONE, phone)
            putString(KEY_EMAIL, email)
            putString(KEY_ADDRESS, address)
            apply()
        }
    }

    fun savePhone(context: Context, phone: String) {
        getPrefs(context).edit().putString(KEY_PHONE, phone).apply()
    }

    fun setLoggedIn(context: Context, loggedIn: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_LOGGED_IN, loggedIn).apply()
    }

    fun isLoggedIn(context: Context): Boolean =
        getPrefs(context).getBoolean(KEY_LOGGED_IN, false)

    fun logout(context: Context) {
        getPrefs(context).edit().clear().apply()
    }

    fun getName(context: Context): String = getPrefs(context).getString(KEY_NAME, "Valued Customer") ?: "Valued Customer"
    fun getPhone(context: Context): String = getPrefs(context).getString(KEY_PHONE, "") ?: ""
    fun getEmail(context: Context): String = getPrefs(context).getString(KEY_EMAIL, "") ?: ""
    fun getAddress(context: Context): String = getPrefs(context).getString(KEY_ADDRESS, "") ?: ""
}
