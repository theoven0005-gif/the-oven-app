package com.example.theoven

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

object NotificationHelper {

    private const val CHANNEL_ID = "order_updates_channel"
    private const val CHANNEL_NAME = "Order & Payment Updates"
    private const val CHANNEL_DESC = "Notifications regarding your order status and payment verification."

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESC
                enableVibration(true)
            }
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    fun showPaymentNotification(context: Context, order: OrderModel) {
        createNotificationChannel(context)

        val intent = Intent(context, TrackerActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val (title, message) = when (order.status) {
            PaymentStatus.APPROVED -> Pair(
                "Payment Approved! 🎉",
                "Your payment of Rs. ${order.totalAmount} for Order #${order.orderId} was verified. Your food is now being prepared at The Oven!"
            )
            PaymentStatus.REJECTED -> Pair(
                "Payment Rejected ❌",
                "Your payment proof for Order #${order.orderId} could not be verified. Please contact branch support or re-upload."
            )
            PaymentStatus.COD_CONFIRMED -> Pair(
                "Order Placed (Cash on Delivery) 🛵",
                "Order #${order.orderId} confirmed! Total amount Rs. ${order.totalAmount} to be paid on delivery."
            )
            else -> Pair(
                "Payment Received ⏳",
                "Order #${order.orderId} submitted! Our team is verifying your payment screenshot."
            )
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_pizza)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        try {
            val notificationManager = NotificationManagerCompat.from(context)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                notificationManager.notify(order.orderId.hashCode(), builder.build())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
