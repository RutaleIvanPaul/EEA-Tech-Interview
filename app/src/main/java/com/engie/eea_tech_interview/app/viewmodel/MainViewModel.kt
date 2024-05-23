package com.engie.eea_tech_interview.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.engie.eea_tech_interview.domain.model.SearchResult
import com.engie.eea_tech_interview.domain.usecases.GetMoviesUseCase
import kotlinx.coroutines.launch

class MainViewModel(private val getMoviesUseCase: GetMoviesUseCase) : ViewModel() {
    private val _searchedMovies = MutableLiveData<SearchResult>()
    val searchedMovies: LiveData<SearchResult> = _searchedMovies

    private val _nowPlayingMovies = MutableLiveData<SearchResult>()
    val nowPlayingMovies: LiveData<SearchResult> = _nowPlayingMovies

    private val _headerText = MutableLiveData<String>()
    val headerText:LiveData<String> = _headerText

    fun searchMovies(query: String) {
        _headerText.value = "Results matching query '${query}' ... "
        viewModelScope.launch {
            try {
                val result = getMoviesUseCase.searchMovies(query)
                _searchedMovies.value = result
                if (result.results.isNullOrEmpty()){
                    _headerText.value = "No Results matching query '${query}' ... "
                }
            } catch (e: Exception) {
                // To handle any errors
            }
        }
    }

    fun getNowPlayingMovies() {
        _headerText.value = "Now Playing"
        if (_nowPlayingMovies.value != null) {
            _nowPlayingMovies.value = _nowPlayingMovies.value
            return
        }

        viewModelScope.launch {
            try {
                val result = getMoviesUseCase.getNowPlayingMovies()
                _nowPlayingMovies.value = result
            } catch (e: Exception) {
                // To handle any errors
            }
        }
    }
}
