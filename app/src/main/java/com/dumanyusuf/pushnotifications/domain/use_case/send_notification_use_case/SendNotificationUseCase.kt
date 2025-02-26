package com.dumanyusuf.pushnotifications.domain.use_case.send_notification_use_case

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.dumanyusuf.pushnotifications.MainActivity
import com.dumanyusuf.pushnotifications.R
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SendNotificationUseCase @Inject constructor(
    private val context: Context,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    suspend operator fun invoke(title: String = "Yeni Bildirim", message: String = "Hoş geldiniz!"): Boolean {
        return try {
            // Bildirimi göster
            showNotification(title, message)

            // Mevcut kullanıcıyı kontrol et
            val currentUser = auth.currentUser
            if (currentUser != null) {
                // Device token'i al
                val token = FirebaseMessaging.getInstance().token.await()
                
                // Bildirimi Firestore'a kaydet
                val notificationData = hashMapOf(
                    "title" to title,
                    "message" to message,
                    "timestamp" to Timestamp.now(),
                    "userId" to currentUser.uid
                )
                
                // User/notifications koleksiyonuna kaydet
                firestore.collection("User")
                    .document(currentUser.uid)
                    .collection("notifications")
                    .add(notificationData)
                    .await()
                
                // Device token'i kaydet
                firestore.collection("User")
                    .document(currentUser.uid)
                    .collection("deviceTokens")
                    .document(token)
                    .set(hashMapOf(
                        "token" to token,
                        "timestamp" to Timestamp.now()
                    ))
                    .await()
                
                true
            } else {
                Log.e("SendNotification", "Kullanıcı giriş yapmamış")
                false
            }
        } catch (e: Exception) {
            Log.e("SendNotification", "Bildirim işleminde hata: ${e.message}")
            false
        }
    }

    private fun showNotification(title: String, message: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "LOCAL_CHANNEL",
                "Yerel Bildirimler",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Uygulama Bildirimleri"
                enableLights(true)
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, "LOCAL_CHANNEL")
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
