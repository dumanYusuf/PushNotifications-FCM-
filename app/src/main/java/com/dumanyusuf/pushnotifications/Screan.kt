package com.dumanyusuf.pushnotifications

sealed class Screan(val route:String) {

    object LoginScrean:Screan("login")
    object RegisterScrean:Screan("register")
    object HomePageScrean:Screan("home_page")

}