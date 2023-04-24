package com.example.movieapp.di

import com.example.movieapp.data.repository.auth.FirebaseAuthImpl
import com.example.movieapp.domain.repository.auth.FirebaseAuthentication
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    fun bindsFirebaseAuthentication(firebaseAuthImpl: FirebaseAuthImpl): FirebaseAuthentication
}