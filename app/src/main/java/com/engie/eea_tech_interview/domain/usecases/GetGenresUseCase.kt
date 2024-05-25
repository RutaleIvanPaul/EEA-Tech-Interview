package com.engie.eea_tech_interview.domain.usecases

import com.engie.eea_tech_interview.domain.model.GenreResult
import com.engie.eea_tech_interview.domain.repositoryservice.MovieRepository

class GetGenresUseCase(private val repository: MovieRepository) {
    suspend fun getGenres():GenreResult{
        return repository.getGenres()
    }
}