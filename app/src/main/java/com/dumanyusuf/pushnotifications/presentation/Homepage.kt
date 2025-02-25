package com.dumanyusuf.pushnotifications.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dumanyusuf.pushnotifications.R
import com.dumanyusuf.pushnotifications.Screan


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage(
    navController: NavController
) {

    val viewModel: HomePageViewModel = hiltViewModel()
    val homePageState = viewModel.homePageState.collectAsState().value
    
    LaunchedEffect(homePageState.isLoggedOut) {
        if (homePageState.isLoggedOut) {
            navController.navigate(Screan.LoginScrean.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }
    

    
    if (homePageState.error.isNotEmpty()) {
        Text(
            text = homePageState.error,
            color = androidx.compose.ui.graphics.Color.Red,
            modifier = Modifier.padding(16.dp)
        )
    }

    Scaffold (
        topBar = {
            TopAppBar(title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Hoşgeldiniz")
                   TextButton(onClick = { viewModel.logOut() }) {
                       Text(text = "Çıkış Yap")
                   }
                    Icon(
                        modifier = Modifier.clickable {
                            navController.navigate(Screan.NotificationsScrean.route)
                        },
                        painter = painterResource(R.drawable.notifications),
                        contentDescription = "")
                }
            })
        },
        content = {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(it)) {
                Button(
                    onClick = {
                        // butona basınca bildirim olussusn
                    }
                ) {
                    Text(text = "Bildirim olustur")
                }

            }
        }
    )


}

