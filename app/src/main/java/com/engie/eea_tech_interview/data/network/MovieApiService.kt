package com.engie.eea_tech_interview.data.network

import com.engie.eea_tech_interview.domain.model.GenreResult
import com.engie.eea_tech_interview.domain.model.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("search/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
    ): SearchResult

    @GET("genre/movie/list")
    suspend fun getGenre(
        @Query("api_key") apiKey: String
    ): GenreResult

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String
    ): SearchResult
}
