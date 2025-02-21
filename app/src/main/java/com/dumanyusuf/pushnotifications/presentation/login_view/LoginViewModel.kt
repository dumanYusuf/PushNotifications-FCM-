package com.dumanyusuf.pushnotifications.presentation.login_view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumanyusuf.pushnotifications.domain.use_case.login_use_case.LoginUseCase
import com.dumanyusuf.pushnotifications.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) :ViewModel() {


    private val _loginState= MutableStateFlow<LoginState>(LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()


    fun loginUser(email:String,password:String){
        viewModelScope.launch {
            _loginState.value= LoginState(loading = true)
            val result= loginUseCase.loginUser(email, password)
            when(result){
                is Resource.Success -> {
                    _loginState.value = LoginState(
                        succsess = true,
                        user = result.data
                    )
                    Log.e("basarılı", "giriş basarılı")
                }
                is Resource.Loading->{
                    _loginState.value= LoginState(loading = true)
                    Log.e("loading","kayıt loading")
                }
                is Resource.Error->{
                    _loginState.value= LoginState(error = result.message.toString())
                    Log.e("basarısız","kayıt basarısız")
                }
            }
        }
    }

}