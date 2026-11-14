package com.example.theoven

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.SetOptions
import com.google.firebase.messaging.FirebaseMessaging

/**
 * Keeps users/{uid}.fcmToken in sync so the admin panel can push
 * order-status notifications to the right device (once Cloud Functions
 * are deployed; tokens are still worth collecting now).
 *
 * Never throws; failures are reported through the [onDone] result.
 */
object FCMTokenManager {

    /**
     * Fetches the current FCM registration token and saves it to
     * users/{uid}. Call after sign-in (e.g. from LoginActivity / HomeActivity).
     * Refreshes are also picked up automatically via
     * [MyFirebaseMessagingService.onNewToken].
     */
    fun refreshAndSave(onDone: (Result<Unit>) -> Unit = {}) {
        try {
            FirebaseMessaging.getInstance().token
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        onDone(
                            Result.failure(
                                task.exception ?: IllegalStateException("FCM token fetch failed")
                            )
                        )
                        return@addOnCompleteListener
                    }
                    try {
                        saveTokenToFirestore(task.result)
                            .addOnCompleteListener { saveTask ->
                                if (saveTask.isSuccessful) onDone(Result.success(Unit))
                                else onDone(
                                    Result.failure(
                                        saveTask.exception ?: IllegalStateException("Token save failed")
                                    )
                                )
                            }
                    } catch (e: Exception) {
                        onDone(Result.failure(e))
                    }
                }
        } catch (e: Exception) {
            onDone(Result.failure(e))
        }
    }

    /**
     * Writes [token] to users/{uid}.fcmToken (merge — other fields preserved).
     * Returns an already-successful task when nobody is signed in, so it is
     * safe to call from [MyFirebaseMessagingService.onNewToken] at any time.
     */
    fun saveTokenToFirestore(token: String): Task<Void> {
        val uid = FirebaseHelper.currentUid ?: return Tasks.forResult(null)
        return FirebaseHelper.firestore.collection("users").document(uid)
            .set(mapOf("fcmToken" to token), SetOptions.merge())
    }
}
