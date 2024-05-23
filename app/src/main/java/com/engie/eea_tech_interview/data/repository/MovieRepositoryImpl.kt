package com.engie.eea_tech_interview.data.repository

import com.engie.eea_tech_interview.data.network.MovieApiService
import com.engie.eea_tech_interview.domain.model.GenreResult
import com.engie.eea_tech_interview.domain.model.SearchResult
import com.engie.eea_tech_interview.domain.repositoryservice.MovieRepository

class MovieRepositoryImpl(private val apiService: MovieApiService) : MovieRepository {
    override suspend fun getMovies(apiKey: String, query: String): SearchResult {
        return apiService.getMovies(apiKey, query)
    }

    override suspend fun getGenre(apiKey: String): GenreResult {
        return apiService.getGenre(apiKey)
    }
}
