package com.dumanyusuf.pushnotifications.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumanyusuf.pushnotifications.domain.use_case.home_page_use_case.HomePageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomePageViewModel @Inject constructor(private val homePageUseCase: HomePageUseCase):ViewModel() {


    private val _homePageState = MutableStateFlow(LogoutStata())
    val homePageState: StateFlow<LogoutStata> = _homePageState.asStateFlow()

    fun logOut() {
        viewModelScope.launch {
            try {
                homePageUseCase.logOut()
                _homePageState.value = LogoutStata(isLoggedOut = true)
                Log.e("cıkıs basarılı","cıkıs basarılı")
            } catch (e: Exception) {
                _homePageState.value = LogoutStata(error = e.message ?: "Çıkış yapılırken bir hata oluştu")
            }
        }
    }

}