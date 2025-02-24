package com.dumanyusuf.pushnotifications.domain.repo

import com.dumanyusuf.pushnotifications.domain.model.NotificationModel
import com.dumanyusuf.pushnotifications.util.Resource

interface NotificationRepo {


    suspend fun saveNotification(notification: NotificationModel)
    suspend fun getNotifications():Resource<List<NotificationModel>>

}