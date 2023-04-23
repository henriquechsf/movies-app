package com.example.movieapp.domain.usecases.auth

import com.example.movieapp.domain.repository.auth.FirebaseAuthentication

class RegisterUseCase constructor(
    private val firebaseAuthentication: FirebaseAuthentication
) {

    suspend operator fun invoke(email: String, password: String) {
        firebaseAuthentication.register(email, password)
    }
}