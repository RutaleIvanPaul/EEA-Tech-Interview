package com.engie.eea_tech_interview.domain.usecases

import com.engie.eea_tech_interview.domain.model.SearchResult
import com.engie.eea_tech_interview.domain.repositoryservice.MovieRepository

class GetMoviesUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(apiKey: String, query: String): SearchResult {
        return repository.getMovies(apiKey, query)
    }
}
