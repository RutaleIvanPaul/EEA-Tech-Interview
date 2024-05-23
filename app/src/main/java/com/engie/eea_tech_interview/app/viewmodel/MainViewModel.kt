package com.engie.eea_tech_interview.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.engie.eea_tech_interview.domain.model.SearchResult
import com.engie.eea_tech_interview.domain.usecases.GetMoviesUseCase
import kotlinx.coroutines.launch

class MainViewModel(private val getMoviesUseCase: GetMoviesUseCase) : ViewModel() {
    private val _movies = MutableLiveData<SearchResult>()
    val movies: LiveData<SearchResult> = _movies

    fun fetchMovies(apiKey: String, query: String) {
        viewModelScope.launch {
            try {
                val result = getMoviesUseCase(apiKey, query)
                _movies.value = result
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
