package com.dumanyusuf.pushnotifications.domain.use_case.login_use_case

import com.dumanyusuf.pushnotifications.domain.model.User
import com.dumanyusuf.pushnotifications.domain.repo.AuthRepo
import com.dumanyusuf.pushnotifications.util.Resource
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repo: AuthRepo) {

    suspend fun loginUser(email: String, password: String): Resource<User> {
        return repo.loginUser(email, password)
    }

}