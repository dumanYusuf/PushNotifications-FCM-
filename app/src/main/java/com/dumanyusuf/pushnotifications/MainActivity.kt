package com.dumanyusuf.pushnotifications

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dumanyusuf.pushnotifications.domain.model.User
import com.dumanyusuf.pushnotifications.presentation.HomePage
import com.dumanyusuf.pushnotifications.presentation.login_view.LoginScrean
import com.dumanyusuf.pushnotifications.presentation.register_view.RegisterScrean
import com.dumanyusuf.pushnotifications.service.MyFirebaseMessagingService
import com.dumanyusuf.pushnotifications.ui.theme.PushNotificationsTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLDecoder


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        
        // Tüm kullanıcılar için topic'e abone ol
        FirebaseMessaging.getInstance().subscribeToTopic("all")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FCM", "Subscribed to all topic")
                } else {
                    Log.e("FCM", "Failed to subscribe to all topic", task.exception)
                }
            }
        setContent {
            PushNotificationsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.padding(it)){
                        PageController()
                    }
                }
            }
        }
    }
}


@Composable
fun PageController() {
    val navController = rememberNavController()

    val curentUser=FirebaseAuth.getInstance().currentUser

    NavHost(
        navController = navController,
        startDestination = if (curentUser!=null )Screan.HomePageScrean.route else Screan.LoginScrean.route
    ) {
        composable(Screan.LoginScrean.route) {
            LoginScrean(navController = navController) {
                navController.navigate(Screan.RegisterScrean.route)
            }
        }
        composable(Screan.RegisterScrean.route) {
            RegisterScrean(
                navController = navController,
                nextLoginPage = {
                    navController.navigate(Screan.LoginScrean.route)
                }
            )
        }
        composable(Screan.HomePageScrean.route) {
            HomePage(navController)
        }
    }
}
