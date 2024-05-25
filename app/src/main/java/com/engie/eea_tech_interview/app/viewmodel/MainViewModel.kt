package com.engie.eea_tech_interview.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.engie.eea_tech_interview.domain.model.Genre
import com.engie.eea_tech_interview.domain.model.SearchResult
import com.engie.eea_tech_interview.domain.usecases.GetGenresUseCase
import com.engie.eea_tech_interview.domain.usecases.GetMoviesUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase
) : ViewModel() {
    private val _searchedMovies = MutableLiveData<SearchResult>()
    val searchedMovies: LiveData<SearchResult> = _searchedMovies

    private val _nowPlayingMovies = MutableLiveData<SearchResult>()
    val nowPlayingMovies: LiveData<SearchResult> = _nowPlayingMovies

    private val _headerText = MutableLiveData<String>()
    val headerText: LiveData<String> = _headerText

    private val genresList = mutableListOf<Genre>()
    private val _genres = MutableLiveData<String>()
    val genres: LiveData<String> = _genres

    fun fetchAssociatedGenres(genreIds: List<Int>){
        if (genresList.isNullOrEmpty()) {
            viewModelScope.launch {
                try {
                    val genres = getGenresUseCase.getGenres().genres
                    genresList.addAll(genres)
                    _genres.postValue(filterGenreNamesByIds(genreIds))
                } catch (e: Exception) {
                    // Handle error
                }
            }
        }
        else{
            _genres.postValue(filterGenreNamesByIds(genreIds))
        }
    }

    private fun filterGenreNamesByIds(genreIds: List<Int>): String {
        return genresList
            .filter { genre ->
                genreIds.contains(genre.id)
            }
            .joinToString(", ") { it.name }
    }


    fun searchMovies(query: String) {
        _headerText.value = "Results matching query '${query}' ... "
        viewModelScope.launch {
            try {
                val result = getMoviesUseCase.searchMovies(query)
                _searchedMovies.postValue(result)
                if (result.results.isNullOrEmpty()) {
                    _headerText.postValue("No Results matching query '${query}' ... ")
                }
            } catch (e: Exception) {
                // To handle any errors
            }
        }
    }

    fun getNowPlayingMovies() {
        _headerText.value = "Now Playing"
        if (_nowPlayingMovies.value != null) {
            _nowPlayingMovies.postValue(_nowPlayingMovies.value)
            return
        }

        viewModelScope.launch {
            try {
                val result = getMoviesUseCase.getNowPlayingMovies()
                _nowPlayingMovies.postValue(result)
            } catch (e: Exception) {
                // To handle any errors
            }
        }
    }
}
