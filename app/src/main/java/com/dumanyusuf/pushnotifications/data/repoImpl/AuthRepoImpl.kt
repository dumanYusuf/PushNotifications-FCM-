package com.dumanyusuf.pushnotifications.data.repoImpl

import com.dumanyusuf.pushnotifications.domain.model.User
import com.dumanyusuf.pushnotifications.domain.repo.AuthRepo
import com.dumanyusuf.pushnotifications.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
):AuthRepo {

    override suspend fun registerUser(email: String, password: String, userNameAndLastName: String): Resource<User> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()

            val firebaseUser = authResult.user
            if (firebaseUser != null) {
                val newUser = User(
                    userId = firebaseUser.uid,
                    userName = email,
                    userNameAndLastName = userNameAndLastName
                )
                firestore.collection("User").document(firebaseUser.uid).set(newUser).await()
                Resource.Success(newUser)
            } else {
                Resource.Error("user not found")
            }
        } catch (e: Exception) {
            Resource.Error("Hata cıktı: ${e.localizedMessage}")
        }
    }

    override suspend fun loginUser(email: String, password: String): Resource<User> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user
            
            if (firebaseUser != null) {
                val userDoc = firestore.collection("User").document(firebaseUser.uid).get().await()
                if (userDoc.exists()) {
                    val user = userDoc.toObject(User::class.java)
                    if (user != null) {
                        Resource.Success(user)
                    } else {
                        Resource.Error("Kullanıcı bilgileri alınamadı")
                    }
                } else {
                    Resource.Error("Kullanıcı bulunamadı")
                }
            } else {
                Resource.Error("Giriş başarısız")
            }
        } catch (e: Exception) {
            Resource.Error("Hata oluştu: ${e.localizedMessage}")
        }
    }


}