package com.example.movieapp.presenter.auth.forgot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movieapp.domain.usecases.auth.ForgotUseCase
import com.example.movieapp.domain.usecases.auth.LoginUseCase
import com.example.movieapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ForgotViewModel @Inject constructor(
    private val forgotUseCase: ForgotUseCase
) : ViewModel() {

    fun forgot(email: String) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            forgotUseCase(email)

            emit(StateView.Success(Unit))
        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(StateView.Error(message = exception.message))
        }
    }
}