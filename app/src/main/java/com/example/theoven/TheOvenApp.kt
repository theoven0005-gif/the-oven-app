package com.example.theoven

import android.app.Application

/**
 * Application class: enables Firestore's offline cache before any
 * Firestore access happens. Registered in AndroidManifest.xml.
 */
class TheOvenApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseHelper.configureOfflineCache()
    }
}
