package com.engie.eea_tech_interview.domain.repositoryservice

import com.engie.eea_tech_interview.domain.model.GenreResult
import com.engie.eea_tech_interview.domain.model.SearchResult

interface MovieRepository {
    suspend fun getMovies(query: String): SearchResult
    suspend fun getGenres(): GenreResult

    suspend fun getNowPlayingMovies(): SearchResult
}
