package com.dumanyusuf.pushnotifications.presentation.login_view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dumanyusuf.pushnotifications.Screan
import com.google.gson.Gson
import java.net.URLEncoder
import kotlin.math.log

@Composable
fun LoginScrean(
    viewModel: LoginViewModel= hiltViewModel(),
    navController: NavController,
    nextRegisterPage:()->Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginState=viewModel.loginState.collectAsState().value

    LaunchedEffect(loginState.succsess) {
        if (loginState.succsess) {
            loginState.user?.let { user ->
                val userJson = Gson().toJson(user)
                val encodedUser = URLEncoder.encode(userJson, "UTF-8")
                val route = "${Screan.HomePageScrean.route}/$encodedUser"
                navController.navigate(route) {
                    popUpTo(Screan.LoginScrean.route) { inclusive = true }
                }
            }
        }
    }


    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {


            Text(
                fontSize = 32.sp,
                text = "Hoşgeldiniz")

            CustomTextField(
                value = email,
                onValueChange = {
                email=it
            },
                placeHolder = "Kullanıcı adınızı giriniz"
            )
            Spacer(Modifier.padding(10.dp))
            CustomTextField(
                value = password,
                onValueChange = {
                    password=it
                },
                placeHolder = "Şifrenizi giriniz"
            )

            // Hata mesajını göster
            if(loginState.error.isNotEmpty()) {
                Text(
                    text = loginState.error,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Button(
                onClick = {
                    // giriş yapılacak
                    viewModel.loginUser(email,password)
                }
            ) {
                if(loginState.loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(4.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Giriş yap")
                }
            }





            Spacer(Modifier.padding(10.dp))
            Row (modifier = Modifier.fillMaxWidth().padding(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){

               Text(text = "Hesabınız yoksa kayıt olun")
                TextButton(
                    onClick = nextRegisterPage
                        // registera yönlendir

                ) {
                    Text(text = "Kayıt ol")
                }
            }
        }
    }

}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value:String,
    onValueChange:(String)->Unit,
    placeHolder:String
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth().padding(20.dp),
        value=value,
        onValueChange ={
           onValueChange(it)
        },
        placeholder = { Text(placeHolder) },
    )
}


