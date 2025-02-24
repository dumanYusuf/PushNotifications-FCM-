package com.dumanyusuf.pushnotifications.domain.model

import com.google.firebase.Timestamp

data class NotificationModel(
    val id:String,
    val title:String,
    val content:String,
    val timestamp: Timestamp=Timestamp.now()
)
