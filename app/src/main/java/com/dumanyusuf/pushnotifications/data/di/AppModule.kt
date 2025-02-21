package com.dumanyusuf.pushnotifications.data.di

import com.dumanyusuf.pushnotifications.data.repoImpl.AuthRepoImpl
import com.dumanyusuf.pushnotifications.domain.repo.AuthRepo
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideAuth():FirebaseAuth=FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesAuthRepo(auth: FirebaseAuth):AuthRepo{
       return AuthRepoImpl(auth)
    }

}