package com.engie.eea_tech_interview.app.view


import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.engie.eea_tech_interview.Constants
import com.engie.eea_tech_interview.R
import com.engie.eea_tech_interview.databinding.ItemMovieBinding
import com.engie.eea_tech_interview.domain.model.Movie

class MoviesAdapter(private val onClick: (Movie) -> Unit) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private val movies = mutableListOf<Movie>()

    inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            Glide.with(binding.moviePoster.context)
                .load("${Constants.BASE_IMAGE_URL}${Constants.IMAGE_SIZE}${movie.posterPath}")
                .error(R.drawable.placeholder)
                .transform(CenterInside())
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

    fun updateMovies(newMovies: List<Movie>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }
}


