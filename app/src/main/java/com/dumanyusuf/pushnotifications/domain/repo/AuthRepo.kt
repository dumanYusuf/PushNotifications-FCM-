package com.dumanyusuf.pushnotifications.domain.repo

import com.dumanyusuf.pushnotifications.domain.model.User
import com.dumanyusuf.pushnotifications.util.Resource

interface AuthRepo {


    suspend fun registerUser(userName:String,userNameAndLastName:String):Resource<User>

}