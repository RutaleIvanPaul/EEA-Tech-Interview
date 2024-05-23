package com.engie.eea_tech_interview.app.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.engie.eea_tech_interview.R
import com.engie.eea_tech_interview.app.viewmodel.MainViewModel
import com.engie.eea_tech_interview.databinding.FragmentHomeBinding
import com.engie.eea_tech_interview.domain.model.Movie
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by viewModel()
    private var searchJob: Job? = null
    private lateinit var moviesAdapter: MoviesAdapter

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

        initRecyclerView()

        initObservers()

        initListeners()

        if (viewModel.searchedMovies.value?.results.isNullOrEmpty()) {
            viewModel.getNowPlayingMovies()
        }

    }

    private fun initListeners() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                searchJob?.cancel()
                if (s.isNullOrEmpty()) {
                    viewModel.getNowPlayingMovies()
                } else {
                    searchJob = lifecycleScope.launch {
                        delay(300) // Adding a small delay to avoid too many requests
                        viewModel.searchMovies(s.toString())
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.progressBar.visibility = View.VISIBLE
            }
        })
    }

    private fun initObservers() {
        viewModel.searchedMovies.observe(viewLifecycleOwner, Observer { movies ->
            moviesAdapter.updateMovies(movies.results)
            binding.progressBar.visibility = View.GONE
        })

        viewModel.nowPlayingMovies.observe(viewLifecycleOwner, Observer { movies ->
            moviesAdapter.updateMovies(movies.results)
            binding.progressBar.visibility = View.GONE
        })

        viewModel.headerText.observe(viewLifecycleOwner) {
            binding.header.text = it
        }
    }

    private fun initRecyclerView() {
        moviesAdapter = MoviesAdapter { movie ->
            showDetailsFragment(movie)
        }
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = moviesAdapter
        }

    }

    private fun showDetailsFragment(movie: Movie) {
        val fragment = DetailsFragment.newInstance(movie)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment()
    }
}

