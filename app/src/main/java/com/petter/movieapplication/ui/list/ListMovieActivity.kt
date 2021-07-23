package com.petter.movieapplication.ui.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.petter.entities.Movie
import com.petter.entities.MovieCategory
import com.petter.entities.MovieType
import com.petter.movieapplication.R
import com.petter.movieapplication.databinding.ActivityListMovieBinding
import com.petter.movieapplication.ui.detail.DetailActivity
import com.petter.movieapplication.ui.movie.MainMovieAdapter
import com.petter.movieapplication.ui.search.SearchMovieActivity
import com.petter.movieapplication.utils.MOVIES
import com.petter.movieapplication.utils.MOVIE_CATEGORY
import com.petter.movieapplication.utils.MOVIE_TYPE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ListMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListMovieBinding

    private val listViewModel: ListMovieViewModel by viewModels()
    private val movieAdapter: MainMovieAdapter by lazy { MainMovieAdapter(this::openDetail) }
    private var movieType: MovieType = MovieType.MOVIES

    companion object {
        fun start(
            context: Context,
            movies: ArrayList<Movie>,
            movieCategory: MovieCategory,
            movieType: MovieType
        ) {
            context.startActivity(Intent(context, ListMovieActivity::class.java).apply {
                putParcelableArrayListExtra(MOVIES, movies)
                putExtra(MOVIE_CATEGORY, movieCategory.name)
                putExtra(MOVIE_TYPE, movieType.name)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadData()
        observe()
        setUpView()
    }

    private fun setUpView() {
        with(binding.listRecycler) {
            adapter = movieAdapter
        }
        with(binding.listSearch) {
            hint = "${getString(R.string.search)} ${movieType.name.lowercase()}"
            setOnClickListener {
                SearchMovieActivity.start(this@ListMovieActivity, movieType)
            }
        }
    }

    private fun loadData() {
        val moviePage: List<Movie> =
            intent.getParcelableArrayListExtra(MOVIES) ?: arrayListOf()
        val movieCategory: MovieCategory =
            MovieCategory.valueOf(
                intent.getStringExtra(MOVIE_CATEGORY) ?: MovieCategory.POPULAR.name
            )
        movieType =
            MovieType.valueOf(intent.getStringExtra(MOVIE_TYPE) ?: MovieType.MOVIES.name)
        listViewModel.config(moviePage, movieCategory, movieType)
    }

    private fun observe() {
        lifecycleScope.launchWhenStarted {
            listViewModel.popularMovieListStateFlow.collect {
                movieAdapter.items = it
            }
        }
    }

    private fun openDetail(movie: Movie) {
        DetailActivity.start(this, movie.id, movieType)
    }

}