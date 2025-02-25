package com.dumanyusuf.pushnotifications.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.dumanyusuf.pushnotifications.MainActivity
import com.dumanyusuf.pushnotifications.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseNotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Token'ı backend'e gönder
        sendTokenToBackend(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        
        // Bildirim başlığı ve mesajını al
        val title = remoteMessage.notification?.title ?: "Bildirim"
        val message = remoteMessage.notification?.body ?: "Yeni bir mesajınız var"
        
        // Bildirimi göster
        showNotification(title, message)
    }

    private fun showNotification(title: String, message: String) {
        val channelId = "fcm_default_channel"
        
        // Bildirime tıklandığında MainActivity'yi aç
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        // Bildirim kanalı oluştur (Android 8.0 ve üzeri için gerekli)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Firebase Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Bildirimi oluştur
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.notifications)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        // Bildirimi göster
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }

    private fun sendTokenToBackend(token: String) {
        // TODO: Backend'e token'ı gönder
        println("New FCM Token: $token")
    }
}

