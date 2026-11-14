package com.example.theoven

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Receives order-status push notifications sent by the admin panel
 * (once Cloud Functions are deployed on the Blaze plan).
 *
 * Expected data-payload keys: title, body, orderId, type.
 *
 * Manifest registration: service declaration with the MESSAGING_EVENT
 * intent filter (see AndroidManifest.xml).
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Keep users/{uid}.fcmToken fresh even when the app isn't open.
        try {
            FCMTokenManager.saveTokenToFirestore(token)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val data = message.data
        val title = data["title"] ?: message.notification?.title ?: "The Oven"
        val body = data["body"]
            ?: message.notification?.body
            ?: "Your order status has been updated."
        val orderId = data["orderId"]

        try {
            showOrderNotification(title, body, orderId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showOrderNotification(title: String, body: String, orderId: String?) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Order updates",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply { description = "Status updates for your The Oven orders" }
            notificationManager.createNotificationChannel(channel)
        }

        // Tapping opens the tracker for this order (Home when no order ID).
        // TrackerActivity already reads the "EXTRA_ORDER_ID" extra.
        val intent = if (orderId != null) {
            Intent(this, TrackerActivity::class.java).apply {
                putExtra("EXTRA_ORDER_ID", orderId)
            }
        } else {
            Intent(this, HomeActivity::class.java)
        }.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            orderId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_pizza_real)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(orderId.hashCode(), notification)
    }

    companion object {
        const val CHANNEL_ID = "order_updates"
    }
}
