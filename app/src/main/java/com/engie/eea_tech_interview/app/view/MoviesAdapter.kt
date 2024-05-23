package com.engie.eea_tech_interview.app.view


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.engie.eea_tech_interview.Constants
import com.engie.eea_tech_interview.databinding.ItemMovieBinding
import com.engie.eea_tech_interview.domain.model.Movie

class MoviesAdapter(private val movies: List<Movie>, private val onClick: (Movie) -> Unit) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movieTitle.text = movie.title
            binding.movieOverview.text = movie.overview
            Glide.with(binding.moviePoster.context)
                .load(Constants.BASE_URL + movie.posterPath)
                .into(binding.moviePoster)

            binding.root.setOnClickListener {
                onClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.size
}
