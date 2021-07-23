package com.petter.movieapplication.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petter.entities.Movie
import com.petter.movieapplication.databinding.RowSearchMovieBinding
import com.petter.movieapplication.utils.loadImage

typealias SearchItemClickListener = (Movie) -> Unit

class SearchMovieAdapter(private val searchItemClickListener: SearchItemClickListener) :
    ListAdapter<Movie, SearchMovieAdapter.SearchViewHolder>(DiffCallback()) {

    fun addData(list: List<Movie>) {
        submitList(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            RowSearchMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), searchItemClickListener
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SearchViewHolder(
        private val binding: RowSearchMovieBinding,
        private val searchItemClickListener: SearchItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            with(binding) {
                searchMovieImage.loadImage("https://image.tmdb.org/t/p/w300/${item.posterPath}")
                searchMovieTitle.text = item.title
                searchMovieShortSummary.text = item.summary
                searchMovieRate.text = item.voteAverage.toString()
            }
            binding.root.setOnClickListener {
                searchItemClickListener(item)
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem

}