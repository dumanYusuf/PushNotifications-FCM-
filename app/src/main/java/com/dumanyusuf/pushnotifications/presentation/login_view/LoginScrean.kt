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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScrean(
    nextRegisterPage:()->Unit
) {

    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                value = password,
                onValueChange = {
                    password=it
                },
                placeHolder = "Şifrenizi giriniz"
            )

            Button(
                onClick = {
                    // giriş yapılacak
                }
            ) {
                Text("Giriş Yap")
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


