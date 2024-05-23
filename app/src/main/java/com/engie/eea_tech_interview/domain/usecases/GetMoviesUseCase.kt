package com.engie.eea_tech_interview.domain.usecases

import com.engie.eea_tech_interview.domain.model.Movie
import com.engie.eea_tech_interview.domain.model.SearchResult
import com.engie.eea_tech_interview.domain.repositoryservice.MovieRepository

class GetMoviesUseCase(private val repository: MovieRepository) {

    suspend fun getNowPlayingMovies(): SearchResult {
        return repository.getNowPlayingMovies()
    }

    suspend fun searchMovies( query: String): SearchResult {
        return repository.getMovies(query)
    }
}

