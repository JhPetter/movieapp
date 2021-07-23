package com.petter.movieapplication.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.petter.entities.Movie
import com.petter.entities.MovieType
import com.petter.movieapplication.databinding.ActivitySearchMovieBinding
import com.petter.movieapplication.ui.detail.DetailActivity
import com.petter.movieapplication.utils.MOVIE_TYPE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchMovieBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private val movieAdapter: SearchMovieAdapter by lazy { SearchMovieAdapter(this::openDetail) }

    companion object {
        fun start(context: Context, movieType: MovieType) {
            context.startActivity(Intent(context, SearchMovieActivity::class.java).apply {
                putExtra(MOVIE_TYPE, movieType.name)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observer()
        setUpView()
    }

    private fun setUpView() {
        binding.searchResults.adapter = movieAdapter
        binding.searchQuery.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH)
                searchViewModel.search(binding.searchQuery.text.toString(), fetchMovieType())
            true
        }
    }

    private fun fetchMovieType(): MovieType =
        MovieType.valueOf(intent.getStringExtra(MOVIE_TYPE) ?: MovieType.MOVIES.name)

    private fun observer() {
        lifecycleScope.launchWhenStarted {
            searchViewModel.movieListStateFlow.collect {
                movieAdapter.submitList(it)
            }
        }
    }

    private fun openDetail(movie: Movie) {
        DetailActivity.start(this, movie.id, fetchMovieType())
    }
}