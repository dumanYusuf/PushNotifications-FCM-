package com.dumanyusuf.pushnotifications.data.repoImpl

import android.util.Log
import com.dumanyusuf.pushnotifications.domain.model.NotificationModel
import com.dumanyusuf.pushnotifications.domain.repo.NotificationRepo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NotificationRepoImpl @Inject constructor(private val firebasse:FirebaseFirestore):NotificationRepo {


    override suspend fun saveNotification(notification: NotificationModel) {
        try {
            Log.d("NotificationRepo", "Saving notification: $notification")
            firebasse.collection("notifications")
                .document(notification.id)
                .set(notification)
                .await()
            Log.d("NotificationRepo", "Notification saved successfully")
        } catch (e: Exception) {
            Log.e("NotificationRepo", "Error saving notification", e)
            throw e
        }
    }


}