package com.engie.eea_tech_interview.domain.usecases

import com.engie.eea_tech_interview.domain.model.GenreResult
import com.engie.eea_tech_interview.domain.repositoryservice.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetGenresUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var getGenresUseCase: GetGenresUseCase

    @Before
    fun setUp() {
        movieRepository = mockk()
        getGenresUseCase = GetGenresUseCase(movieRepository)
    }

    @Test
    fun `getGenres returns GenreResult`() = runBlocking {
        val expectedResponse = GenreResult(genres = listOf())
        coEvery { movieRepository.getGenres() } returns expectedResponse

        val actualResponse = getGenresUseCase.getGenres()

        assertEquals(expectedResponse, actualResponse)
        coVerify { movieRepository.getGenres() }
    }
}