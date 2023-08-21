package com.example.movieapp.presenter.main.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movieapp.BuildConfig
import com.example.movieapp.domain.usecases.movie.GetCreditsUseCase
import com.example.movieapp.domain.usecases.movie.GetMovieDetailsUseCase
import com.example.movieapp.util.Constants
import com.example.movieapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getCreditsUseCase: GetCreditsUseCase
) : ViewModel() {

    private val _movieId = MutableLiveData(0)
    val movieId: LiveData<Int> = _movieId

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

    fun getCredits(movieId: Int) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val movie = getCreditsUseCase(
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

    fun setMovieId(id: Int) {
        _movieId.value = id
    }
}