package com.example.movieapp.domain.usecases.movie

import com.example.movieapp.data.mapper.toDomain
import com.example.movieapp.domain.model.Credit
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.repository.movie.MovieDetailsRepository
import javax.inject.Inject

class GetMoviesSimilarUseCase @Inject constructor(
    private val repository: MovieDetailsRepository
) {

    suspend operator fun invoke(apiKey: String, language: String?, movieId: Int): List<Movie> {
        return repository.getMoviesSimilar(
            apiKey = apiKey,
            language = language,
            movieId = movieId
        ).map { it.toDomain() }
    }
}
