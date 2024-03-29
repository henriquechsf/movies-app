package com.example.movieapp.domain.usecases.movie

import com.example.movieapp.data.mapper.toDomain
import com.example.movieapp.domain.model.MovieReview
import com.example.movieapp.domain.repository.movie.MovieDetailsRepository
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(
    private val repository: MovieDetailsRepository
) {

    suspend operator fun invoke(
        apiKey: String,
        language: String?,
        movieId: Int
    ): List<MovieReview> {
        return repository.getMovieReviews(
            apiKey = apiKey,
            language = language,
            movieId = movieId
        ).map { it.toDomain() }
    }
}
