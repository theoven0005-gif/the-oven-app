package com.example.theoven

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage

/**
 * Central access point for Firebase services.
 *
 * No manual init is required: the google-services Gradle plugin registers
 * FirebaseInitProvider, which initializes FirebaseApp automatically at startup.
 * These lazies simply hand you the default instances.
 *
 * All accessors are safe to call even when Firebase is unreachable —
 * calls that need the network fail through Tasks/coroutines, never crashes.
 */
object FirebaseHelper {

    val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    val storage: FirebaseStorage by lazy { FirebaseStorage.getInstance() }

    /** UID of the currently signed-in user, or null when signed out. Never throws. */
    val currentUid: String?
        get() = try {
            auth.currentUser?.uid
        } catch (e: Exception) {
            null
        }

    fun isSignedIn(): Boolean = currentUid != null

    /**
     * Enable Firestore's offline cache. Call once, early (Application.onCreate),
     * before any Firestore access. Safe to call even if Firebase init failed.
     */
    fun configureOfflineCache() {
        try {
            firestore.firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Creates/updates the user's profile document at users/{uid}.
     * Uses SetOptions.merge() so existing fields (e.g. fcmToken) are preserved.
     *
     * Schema: users/{uid} = { name, email, phone, fcmToken, createdAt }
     */
    fun ensureUserDocument(
        name: String,
        email: String,
        phone: String
    ): Task<Void> {
        val uid = requireNotNull(currentUid) {
            "No signed-in user — call ensureUserDocument() after FirebaseAuth sign-in."
        }
        val data = mapOf(
            "name" to name,
            "email" to email,
            "phone" to phone,
            "createdAt" to FieldValue.serverTimestamp()
        )
        return firestore.collection("users").document(uid)
            .set(data, SetOptions.merge())
    }

    /** Sign out the current user. Never throws. */
    fun signOut() {
        try {
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
