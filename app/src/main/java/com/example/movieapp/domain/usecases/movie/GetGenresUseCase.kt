package com.example.movieapp.domain.usecases.movie

import com.example.movieapp.data.mapper.toDomain
import com.example.movieapp.domain.model.Genre
import com.example.movieapp.domain.repository.movie.MovieRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(apiKey: String, language: String?): List<Genre> {
        return repository.getGenre(
            apiKey = apiKey,
            language = language
        ).genres?.map { it.toDomain() } ?: emptyList()
    }
}