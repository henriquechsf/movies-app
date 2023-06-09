package com.example.movieapp.presenter.main.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movieapp.BuildConfig
import com.example.movieapp.domain.usecases.movie.GetMovieDetailsUseCase
import com.example.movieapp.util.Constants
import com.example.movieapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    fun getMovieDetails(movieId: Int) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val movie = getMovieDetailsUseCase(
                apiKey = BuildConfig.API_KEY,
                language = Constants.Movie.LANGUAGE,
                movieId = movieId
            )

            emit(StateView.Success(movie))
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        }
    }
}