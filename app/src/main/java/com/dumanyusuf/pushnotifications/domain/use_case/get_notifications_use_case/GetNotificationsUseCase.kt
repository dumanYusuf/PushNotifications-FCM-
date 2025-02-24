package com.dumanyusuf.pushnotifications.domain.use_case.get_notifications_use_case

import com.dumanyusuf.pushnotifications.domain.model.NotificationModel
import com.dumanyusuf.pushnotifications.domain.repo.NotificationRepo
import com.dumanyusuf.pushnotifications.util.Resource
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(private val repo: NotificationRepo) {


    suspend fun getNotificationsUseCase():Resource<List<NotificationModel>>{
        return repo.getNotifications()
    }

}