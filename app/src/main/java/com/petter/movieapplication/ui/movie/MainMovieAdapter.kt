package com.petter.movieapplication.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petter.entities.Movie
import com.petter.movieapplication.databinding.RowMovieBinding
import com.petter.movieapplication.utils.loadImage

typealias MovieClickListener = (Movie) -> Unit

class MainMovieAdapter(private val movieClickListener: MovieClickListener) :
    ListAdapter<Movie, MainMovieAdapter.MainMovieViewHolder>(MoviesDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMovieViewHolder {
        return MainMovieViewHolder(
            RowMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            movieClickListener
        )
    }

    override fun onBindViewHolder(holder: MainMovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class MainMovieViewHolder(
        private val binding: RowMovieBinding,
        private val movieClickListener: MovieClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            with(binding) {
                movieTitle.text = item.title
                movieImage.loadImage("https://image.tmdb.org/t/p/w300/${item.posterPath}")
            }
            binding.root.setOnClickListener {
                movieClickListener(item)
            }
        }
    }
}

class MoviesDiffUtilCallBack : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}