package com.example.movieapp.di

import com.example.movieapp.data.repository.auth.FirebaseAuthImpl
import com.example.movieapp.data.repository.movie.MovieDetailsRepositoryImpl
import com.example.movieapp.data.repository.movie.MovieRepositoryImpl
import com.example.movieapp.domain.repository.auth.FirebaseAuthentication
import com.example.movieapp.domain.repository.movie.MovieDetailsRepository
import com.example.movieapp.domain.repository.movie.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    fun bindsFirebaseAuthentication(firebaseAuthImpl: FirebaseAuthImpl): FirebaseAuthentication

    @Binds
    fun bindsMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    fun bindsMovieDetailsRepository(movieDetailsRepositoryImpl: MovieDetailsRepositoryImpl): MovieDetailsRepository
}