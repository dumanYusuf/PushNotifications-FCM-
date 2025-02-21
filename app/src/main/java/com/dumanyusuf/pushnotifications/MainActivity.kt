package com.dumanyusuf.pushnotifications

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dumanyusuf.pushnotifications.presentation.HomePage
import com.dumanyusuf.pushnotifications.presentation.login_view.LoginScrean
import com.dumanyusuf.pushnotifications.presentation.register_view.RegisterScrean
import com.dumanyusuf.pushnotifications.ui.theme.PushNotificationsTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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

    val navController=rememberNavController()

    NavHost(navController=navController, startDestination = Screan.LoginScrean.route){
        composable(Screan.LoginScrean.route) {
           LoginScrean(){
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
            HomePage()
        }
    }

}
