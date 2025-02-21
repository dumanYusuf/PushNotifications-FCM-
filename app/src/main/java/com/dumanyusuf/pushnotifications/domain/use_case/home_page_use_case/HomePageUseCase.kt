package com.dumanyusuf.pushnotifications.domain.use_case.home_page_use_case

import com.dumanyusuf.pushnotifications.domain.repo.AuthRepo
import javax.inject.Inject

class HomePageUseCase @Inject constructor(private val repo: AuthRepo) {

     fun logOut(){
         repo.logOut()
    }

}