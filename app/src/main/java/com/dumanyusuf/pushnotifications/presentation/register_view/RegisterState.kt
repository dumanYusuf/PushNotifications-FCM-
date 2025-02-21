package com.dumanyusuf.pushnotifications.presentation.register_view

import com.dumanyusuf.pushnotifications.domain.model.User

data class RegisterState(
    val succsess:Boolean=false,
    val loading:Boolean=false,
    val error:String="",
    val user: User? = null
)
