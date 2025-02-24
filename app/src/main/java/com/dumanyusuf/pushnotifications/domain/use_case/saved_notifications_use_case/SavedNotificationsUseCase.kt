package com.dumanyusuf.pushnotifications.domain.use_case.saved_notifications_use_case

import com.dumanyusuf.pushnotifications.domain.model.NotificationModel
import com.dumanyusuf.pushnotifications.domain.repo.NotificationRepo
import javax.inject.Inject

class SavedNotificationsUseCase @Inject constructor(private val repo: NotificationRepo) {



    suspend operator fun invoke(notification: NotificationModel) {
        return repo.saveNotification(notification)
    }

}