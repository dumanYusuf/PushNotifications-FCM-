package com.dumanyusuf.pushnotifications.presentation.notifications

import com.dumanyusuf.pushnotifications.domain.model.NotificationModel

data class NotificationsState(
    val notificationsList:List<NotificationModel> = emptyList(),
    val loading:Boolean=false,
    val error:String=""
)
