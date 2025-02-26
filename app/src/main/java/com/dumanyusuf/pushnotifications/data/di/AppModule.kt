package com.dumanyusuf.pushnotifications.data.di

import android.content.Context
import com.dumanyusuf.pushnotifications.data.repoImpl.AuthRepoImpl
import com.dumanyusuf.pushnotifications.data.repoImpl.NotificationRepoImpl
import com.dumanyusuf.pushnotifications.domain.repo.AuthRepo
import com.dumanyusuf.pushnotifications.domain.repo.NotificationRepo
import com.dumanyusuf.pushnotifications.domain.use_case.send_notification_use_case.SendNotificationUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesAuthRepo(auth: FirebaseAuth, firebaseFirestore: FirebaseFirestore): AuthRepo {
        return AuthRepoImpl(auth, firebaseFirestore)
    }

    @Provides
    @Singleton
    fun provideFirebase(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideNotificationRepository(
        firestore: FirebaseFirestore
    ): NotificationRepo {
        return NotificationRepoImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideSendNotificationUseCase(
        @ApplicationContext context: Context,
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): SendNotificationUseCase {
        return SendNotificationUseCase(context, auth, firestore)
    }
}