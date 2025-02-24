package com.dumanyusuf.pushnotifications.presentation.notifications

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dumanyusuf.pushnotifications.R

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotificationsScrean(
    navController: NavController,
    viewModel: NotificationsViewModel = hiltViewModel()
) {

    val notificationsState = viewModel.notificationsState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row {
                    Icon(
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        },
                        painter = painterResource(R.drawable.back), contentDescription = ""
                    )
                    Text(text = "Notifications")
                }
            })
        },
        content = {
            if (notificationsState.loading) {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Red
                )
            } else if (notificationsState.error.isNotEmpty()) {
                Text(text = "Hata: ${notificationsState.error}", modifier = Modifier.padding(top = 80.dp))
            } else {
                LazyColumn(modifier = Modifier.padding(top = 120.dp)) {
                    items(notificationsState.notificationsList) { notification ->
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = notification.title)
                            Text(text = notification.content)
                           // Text(text = notification.timestamp.toString())
                        }
                    }
                }
            }
        }
    )
}
