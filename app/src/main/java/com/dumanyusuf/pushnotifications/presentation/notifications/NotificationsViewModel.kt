package com.dumanyusuf.pushnotifications.presentation.notifications

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumanyusuf.pushnotifications.domain.use_case.get_notifications_use_case.GetNotificationsUseCase
import com.dumanyusuf.pushnotifications.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NotificationsViewModel @Inject constructor(private val getNotificationsUseCase: GetNotificationsUseCase):ViewModel() {

    private val _notificationState=MutableStateFlow<NotificationsState>(NotificationsState())
    val notificationsState:StateFlow<NotificationsState> = _notificationState.asStateFlow()

    init {
       getNotifications()
    }

    fun getNotifications(){
        viewModelScope.launch {
           _notificationState.value=NotificationsState(loading = true)
          val result= getNotificationsUseCase.getNotificationsUseCase()
            when(result){
                is Resource.Success->{
                    _notificationState.value=
                        NotificationsState(notificationsList = result.data?: emptyList())
                    Log.e("basarılı,","veriler geldi")
                }
                is Resource.Error->{
                    _notificationState.value=
                        NotificationsState(error = "Error")
                    Log.e("hata ","hata cıktı:${result.message}")
                }
                is Resource.Loading->{
                    _notificationState.value=
                        NotificationsState(loading =true)
                }
            }

        }
    }

}