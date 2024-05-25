package com.engie.eea_tech_interview.app.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.engie.eea_tech_interview.app.viewmodel.MainViewModel
import com.engie.eea_tech_interview.domain.model.Genre
import com.engie.eea_tech_interview.domain.model.GenreResult
import com.engie.eea_tech_interview.domain.model.SearchResult
import com.engie.eea_tech_interview.domain.usecases.GetGenresUseCase
import com.engie.eea_tech_interview.domain.usecases.GetMoviesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var getMoviesUseCase: GetMoviesUseCase
    private lateinit var getGenresUseCase: GetGenresUseCase
    private lateinit var mainViewModel: MainViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getMoviesUseCase = mockk()
        getGenresUseCase = mockk()
        mainViewModel = MainViewModel(getMoviesUseCase, getGenresUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `fetchAssociatedGenres fetches genres and posts value`() = runBlocking {
        val genreIds = listOf(1, 2, 3)
        val genresResponse = GenreResult(
            genres = listOf(
                Genre(1, "Action"),
                Genre(2, "Comedy"),
                Genre(3, "Drama")
            )
        )
        coEvery { getGenresUseCase.getGenres() } returns genresResponse

        val observer: Observer<String> = mockk(relaxed = true)
        mainViewModel.genres.observeForever(observer)

        mainViewModel.fetchAssociatedGenres(genreIds)

        coVerify { getGenresUseCase.getGenres() }
        verify { observer.onChanged("Action, Comedy, Drama") }

        mainViewModel.genres.removeObserver(observer)
    }

    @Test
    fun `searchMovies fetches search results and posts value`() = runBlocking {
        val searchResult = SearchResult(results = listOf())
        coEvery { getMoviesUseCase.searchMovies(any()) } returns searchResult

        val observer: Observer<SearchResult> = mockk(relaxed = true)
        mainViewModel.searchedMovies.observeForever(observer)

        mainViewModel.searchMovies("test")

        coVerify { getMoviesUseCase.searchMovies("test") }
        verify { observer.onChanged(searchResult) }

        mainViewModel.searchedMovies.removeObserver(observer)
    }

    @Test
    fun `getNowPlayingMovies fetches now playing movies and posts value`() = runBlocking {
        val nowPlayingResult = SearchResult(results = listOf())
        coEvery { getMoviesUseCase.getNowPlayingMovies() } returns nowPlayingResult

        val observer: Observer<SearchResult> = mockk(relaxed = true)
        mainViewModel.nowPlayingMovies.observeForever(observer)

        mainViewModel.getNowPlayingMovies()

        coVerify { getMoviesUseCase.getNowPlayingMovies() }
        verify { observer.onChanged(nowPlayingResult) }

        mainViewModel.nowPlayingMovies.removeObserver(observer)
    }
}