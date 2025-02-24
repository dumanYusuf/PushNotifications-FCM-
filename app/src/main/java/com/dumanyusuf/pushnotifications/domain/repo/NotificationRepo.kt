package com.dumanyusuf.pushnotifications.domain.repo

import com.dumanyusuf.pushnotifications.domain.model.NotificationModel

interface NotificationRepo {


    suspend fun saveNotification(notification: NotificationModel)

}