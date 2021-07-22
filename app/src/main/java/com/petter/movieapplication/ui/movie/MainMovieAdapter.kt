package com.petter.movieapplication.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petter.entities.Movie
import com.petter.movieapplication.databinding.RowMovieBinding
import com.petter.movieapplication.utils.loadImage

class MainMovieAdapter : RecyclerView.Adapter<MainMovieAdapter.MainMovieViewHolder>() {

    var items: List<Movie> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMovieViewHolder {
        return MainMovieViewHolder(
            RowMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainMovieViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class MainMovieViewHolder(private val binding: RowMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            with(binding) {
                movieTitle.text = item.title
                movieDate.text = item.voteAverage.toString()
                movieImage.loadImage("https://image.tmdb.org/t/p/w185/${item.posterPath}")
            }
        }
    }
}