package com.engie.eea_tech_interview.data.repository

import com.engie.eea_tech_interview.data.network.MovieApiService
import com.engie.eea_tech_interview.domain.model.GenreResult
import com.engie.eea_tech_interview.domain.model.SearchResult
import com.engie.eea_tech_interview.domain.repositoryservice.MovieRepository
import com.engie.eea_tech_interview.domainCore.Constants
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MovieRepositoryImplTest {

    private lateinit var movieApiService: MovieApiService
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        movieApiService = mockk()
        movieRepository = MovieRepositoryImpl(movieApiService)
    }

    @Test
    fun `getMovies returns SearchResult`() = runBlocking {
        val expectedResponse = SearchResult(results = listOf())
        coEvery { movieApiService.getMovies(any(), any()) } returns expectedResponse

        val actualResponse = movieRepository.getMovies("test")

        assertEquals(expectedResponse, actualResponse)
        coVerify { movieApiService.getMovies(Constants.MOVIE_API_KEY, "test") }
    }

    @Test
    fun `getNowPlayingMovies returns SearchResult`() = runBlocking {
        val expectedResponse = SearchResult(results = listOf())
        coEvery { movieApiService.getNowPlayingMovies(any()) } returns expectedResponse

        val actualResponse = movieRepository.getNowPlayingMovies()

        assertEquals(expectedResponse, actualResponse)
        coVerify { movieApiService.getNowPlayingMovies(Constants.MOVIE_API_KEY) }
    }

    @Test
    fun `getGenres returns GenreResult`() = runBlocking {
        val expectedResponse = GenreResult(genres = listOf())
        coEvery { movieApiService.getGenres(any()) } returns expectedResponse

        val actualResponse = movieRepository.getGenres()

        assertEquals(expectedResponse, actualResponse)
        coVerify { movieApiService.getGenres(Constants.MOVIE_API_KEY) }
    }
}


