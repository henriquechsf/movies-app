package com.example.movieapp.data.repository.movie

import com.example.movieapp.data.api.ServiceApi
import com.example.movieapp.data.model.CreditResponse
import com.example.movieapp.data.model.MovieResponse
import com.example.movieapp.data.model.MovieReviewResponse
import com.example.movieapp.domain.repository.movie.MovieDetailsRepository
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi
) : MovieDetailsRepository {

    override suspend fun getMovieDetails(
        apiKey: String,
        language: String?,
        movieId: Int
    ): MovieResponse {
        return serviceApi.getMovieDetails(
            apiKey = apiKey,
            language = language,
            movieId = movieId
        )
    }

    override suspend fun getCredits(
        apiKey: String,
        language: String?,
        movieId: Int
    ): CreditResponse {
        return serviceApi.getCredits(
            apiKey = apiKey,
            language = language,
            movieId = movieId
        )
    }

    override suspend fun getMoviesSimilar(
        apiKey: String,
        language: String?,
        movieId: Int
    ): List<MovieResponse> {
        return serviceApi.getMoviesSimilar(
            apiKey = apiKey,
            language = language,
            movieId = movieId
        ).results ?: emptyList()
    }

    override suspend fun getMovieReviews(
        apiKey: String,
        language: String?,
        movieId: Int
    ): List<MovieReviewResponse> {
        return serviceApi.getMovieReviews(
            apiKey = apiKey,
            language = language,
            movieId = movieId
        ).results ?: emptyList()
    }
}