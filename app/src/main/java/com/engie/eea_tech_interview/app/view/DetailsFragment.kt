package com.engie.eea_tech_interview.app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.engie.eea_tech_interview.domainCore.Constants
import com.engie.eea_tech_interview.R
import com.engie.eea_tech_interview.app.viewmodel.MainViewModel
import com.engie.eea_tech_interview.databinding.FragmentDetailsBinding
import com.engie.eea_tech_interview.domain.model.Movie
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: MainViewModel by viewModel()
    private lateinit var movie:Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movie = arguments?.getParcelable<Movie>(ARG_MOVIE) ?: return
        initUI()
        initObservers()
        movie.genreIds?.let { viewModel.fetchAssociatedGenres(it) }
    }

    private fun initObservers() {
        viewModel.genres.observe(viewLifecycleOwner){
            binding.movieGenre.text = it
        }
    }

    private fun initUI() {
        binding.movieTitle.text = movie.title
        binding.movieOverview.text = movie.overview
        binding.movieReleaseDate.text = movie.releaseDate
        binding.movieVoteCount.text = movie.voteCount.toString()
        binding.movieOriginalLanguage.text = movie.originalLanguage
        Glide.with(binding.moviePoster.context)
            .load("${Constants.BASE_IMAGE_URL}${Constants.IMAGE_SIZE}${movie.posterPath}")
            .error(R.drawable.placeholder)
            .transform(CenterInside())
            .into(binding.moviePoster)
    }

    companion object {
        private const val ARG_MOVIE = "movie"

        fun newInstance(movie: Movie): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle().apply {
                putParcelable(ARG_MOVIE, movie)
            }
            fragment.arguments = args
            return fragment
        }
    }
}


