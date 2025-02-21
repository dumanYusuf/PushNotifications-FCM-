package com.dumanyusuf.pushnotifications.domain.repo

import com.dumanyusuf.pushnotifications.domain.model.User
import com.dumanyusuf.pushnotifications.util.Resource

interface AuthRepo {

    suspend fun registerUser(email: String, password: String, userNameAndLastName: String): Resource<User>
    suspend fun loginUser(email: String, password: String): Resource<User>
    fun logOut()
}