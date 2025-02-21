package com.dumanyusuf.pushnotifications.presentation.login_view

import com.dumanyusuf.pushnotifications.domain.model.User

data class LoginState(
    val succsess: Boolean = false,
    val loading: Boolean = false,
    val error: String = "",
    val user: User? = null
)
