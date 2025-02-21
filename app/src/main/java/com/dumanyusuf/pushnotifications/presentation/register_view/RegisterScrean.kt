package com.dumanyusuf.pushnotifications.presentation.register_view

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dumanyusuf.pushnotifications.Screan
import com.dumanyusuf.pushnotifications.presentation.login_view.CustomTextField
import com.dumanyusuf.pushnotifications.util.Resource

@Composable
fun RegisterScrean(
      viewModel: RegisterViewModel= hiltViewModel(),
      nextLoginPage:()->Unit,
      navController: NavController
) {

    var userName by remember { mutableStateOf("") }
    var nameAndLastName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val registerState=viewModel.registerState.collectAsState().value

    LaunchedEffect(registerState.succsess) {
        if(registerState.succsess) {
            navController.navigate(Screan.HomePageScrean.route) {
                popUpTo(Screan.RegisterScrean.route) { inclusive = true }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                fontSize = 32.sp,
                text = "Hoşgeldiniz")

            CustomTextField(
                value = userName,
                onValueChange = {
                    userName=it
                },
                placeHolder = "Kullanıcı adınızı giriniz"
            )
            Spacer(Modifier.padding(10.dp))
            CustomTextField(
                value = nameAndLastName,
                onValueChange = {
                    nameAndLastName=it
                },
                placeHolder = "Adınızı ve Soyadınız giriniz"
            )
            Spacer(Modifier.padding(10.dp))
            CustomTextField(
                value = password,
                onValueChange = {
                    password=it
                },
                placeHolder = "Şifrenizi giriniz"
            )
            Spacer(Modifier.padding(10.dp))
            CustomTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword=it
                },
                placeHolder = "Şifrenizi tekrar giriniz"
            )

            // Hata mesajını göster
            if(registerState.error.isNotEmpty()) {
                Text(
                    text = registerState.error,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Button(
                onClick = {
                    viewModel.registerUser(userName, nameAndLastName)
                },
                enabled = !registerState.loading
            ) {
                if(registerState.loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(4.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Kayıt ol")
                }
            }

         

            Spacer(Modifier.padding(10.dp))
            Row (modifier = Modifier.fillMaxWidth().padding(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){

                Text(text = "Hesabınız varsa giriş yapın")
                TextButton(
                    onClick = nextLoginPage
                        // logine yönlendir
                ) {
                    Text(text = "Giriş yap")
                }
            }
        }
    }

}