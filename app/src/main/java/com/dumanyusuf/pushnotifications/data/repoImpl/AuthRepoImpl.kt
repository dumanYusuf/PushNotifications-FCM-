package com.dumanyusuf.pushnotifications.data.repoImpl

import com.dumanyusuf.pushnotifications.domain.model.User
import com.dumanyusuf.pushnotifications.domain.repo.AuthRepo
import com.dumanyusuf.pushnotifications.util.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(private val auth: FirebaseAuth):AuthRepo {

    override suspend fun registerUser(userName: String, userNameAndLastName: String): Resource<User> {
       return try {

           val authResult=auth.createUserWithEmailAndPassword(userName,userNameAndLastName).await()
           val firebaseUser=authResult.user

           if (firebaseUser!=null){
               val newUser=User(
                   userId = firebaseUser.uid,
                   userName = userName,
                   userNameAndLastName = userNameAndLastName
               )
               Resource.Success(newUser)
           }
           else{
               Resource.Error("user not found")
           }
        }
        catch (e:Exception){
            Resource.Error("Hata cıktı:${e.localizedMessage}")
        }
    }
}