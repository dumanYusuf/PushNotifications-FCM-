package com.dumanyusuf.pushnotifications.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumanyusuf.pushnotifications.domain.use_case.home_page_use_case.HomePageUseCase
import com.dumanyusuf.pushnotifications.domain.use_case.send_notification_use_case.SendNotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val homePageUseCase: HomePageUseCase,
    private val sendNotificationUseCase: SendNotificationUseCase
) : ViewModel() {

    private val _homePageState = MutableStateFlow(LogoutStata())
    val homePageState: StateFlow<LogoutStata> = _homePageState.asStateFlow()

    fun logOut() {
        viewModelScope.launch {
            try {
                homePageUseCase.logOut()
                _homePageState.value = LogoutStata(isLoggedOut = true)
                Log.e("cıkıs basarılı", "cıkıs basarılı")
            } catch (e: Exception) {
                _homePageState.value = LogoutStata(error = e.message ?: "Çıkış yapılırken bir hata oluştu")
            }
        }
    }

    fun sendNotification() {
        viewModelScope.launch {
            try {
                val success = sendNotificationUseCase()
                if (!success) {
                    _homePageState.value = LogoutStata(error = "Bildirim gösterilirken bir hata oluştu")
                }
            } catch (e: Exception) {
                _homePageState.value = LogoutStata(error = e.message ?: "Bildirim gösterilirken bir hata oluştu")
            }
        }
    }
}