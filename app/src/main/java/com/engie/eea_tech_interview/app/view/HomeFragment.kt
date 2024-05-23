package com.engie.eea_tech_interview.app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.engie.eea_tech_interview.R
import com.engie.eea_tech_interview.app.viewmodel.MainViewModel
import com.engie.eea_tech_interview.databinding.FragmentHomeBinding
import kotlinx.coroutines.Job
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by viewModel()
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
            binding.recyclerView.adapter = MoviesAdapter(movies.results) { movie ->
                // Handle item click to open DetailsFragment
            }
        })

        val apiKey = HomeFragment.MOVIE_API_KEY
        val query = HomeFragment.SEARCH_QUERY

        viewModel.fetchMovies(apiKey, query)



//        binding.searchView.doOnTextChanged { text, _, _, _ ->
//            searchJob?.cancel()
//            searchJob = lifecycleScope.launch {
//                delay(300) // Add delay to avoid too many requests
//                viewModel.getMovies(text.toString())
//            }
//        }

        // Initial load
//        viewModel.getMovies("initial query")
    }

    companion object {
        const val MOVIE_API_KEY = "47304f18bd4a3b4e733196b18e68bfbc"
        const val SEARCH_QUERY = "James Bond"
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment()
    }
}

