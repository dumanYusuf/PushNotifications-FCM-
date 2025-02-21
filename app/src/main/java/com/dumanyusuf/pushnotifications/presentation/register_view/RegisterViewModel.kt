package com.dumanyusuf.pushnotifications.presentation.register_view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumanyusuf.pushnotifications.domain.use_case.register_use_case.RegisterUseCase
import com.dumanyusuf.pushnotifications.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel  @Inject constructor(
    private val registerUseCase: RegisterUseCase
):ViewModel() {


    private val _registerState= MutableStateFlow<RegisterState>(RegisterState())
    val registerState:StateFlow<RegisterState> =_registerState.asStateFlow()


    fun registerUser(email: String, password: String, userNameAndLastName: String) {
        viewModelScope.launch {
            _registerState.value = RegisterState(loading = true)
            val result = registerUseCase.registerUser(email, password, userNameAndLastName)
            when(result) {
                is Resource.Success -> {
                    _registerState.value = RegisterState(
                        succsess = true,
                        user = result.data
                    )
                    Log.e("basarılı", "kayıt basarılı")
                }
                is Resource.Loading->{
                    _registerState.value=RegisterState(loading = true)
                    Log.e("loading","kayıt loading")
                }
                is Resource.Error->{
                    _registerState.value=RegisterState(error = result.message.toString())
                    Log.e("basarısız","kayıt basarısız")
                }
            }
        }
    }


}