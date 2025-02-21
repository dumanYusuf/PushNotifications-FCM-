package com.dumanyusuf.pushnotifications.domain.use_case.register_use_case

import com.dumanyusuf.pushnotifications.domain.model.User
import com.dumanyusuf.pushnotifications.domain.repo.AuthRepo
import com.dumanyusuf.pushnotifications.util.Resource
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repo: AuthRepo) {


    suspend fun registerUser(
        email: String,
        password: String,
        userNameAndLastName: String
    ): Resource<User> {
        return repo.registerUser(email, password, userNameAndLastName)
    }

}