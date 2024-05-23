package com.engie.eea_tech_interview.app.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import com.engie.eea_tech_interview.data.network.MovieApiService
import com.engie.eea_tech_interview.R
import com.engie.eea_tech_interview.app.viewmodel.MainViewModel
import com.engie.eea_tech_interview.domain.model.SearchResult
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val retrofit: Retrofit by inject()
    private val viewModel: MainViewModel by viewModel()

    companion object {
        const val MOVIE_API_KEY = "47304f18bd4a3b4e733196b18e68bfbc"
        const val SEARCH_QUERY = "James Bond"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fragment_container, HomeFragment())
            }
        }

        val apiKey = MainActivity.MOVIE_API_KEY
        val query = MainActivity.SEARCH_QUERY

        viewModel.movies.observe(this, Observer { result ->
            Log.d("MOvIES",result.toString())
        })

        viewModel.fetchMovies(apiKey, query)

    }
}