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
import com.dumanyusuf.pushnotifications.domain.model.NotificationModel
import com.dumanyusuf.pushnotifications.domain.use_case.saved_notifications_use_case.SavedNotificationsUseCase
import com.google.firebase.Timestamp
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import dagger.hilt.android.AndroidEntryPoint
import org.checkerframework.checker.units.qual.A
import java.util.UUID
import javax.inject.Inject


@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var saveNotificationUseCase: SavedNotificationsUseCase

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification?.title ?: "Yeni Bildirim"
        val message = remoteMessage.notification?.body ?: ""


        // Bildirimi Firestore'a kaydet
        val notification = NotificationModel(
            id = UUID.randomUUID().toString(),
            title = title,
            content = message,
            timestamp = Timestamp.now()
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                saveNotificationUseCase(notification)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        showNotification(title, message)
    }
    

    private fun showNotification(title: String, message: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("FCM_CHANNEL", "Bildirimler", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }


        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)


        val notification = NotificationCompat.Builder(this, "FCM_CHANNEL")
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.notifications)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}
