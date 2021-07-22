package com.petter.movieapplication.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.petter.entities.Movie
import com.petter.entities.MovieType
import com.petter.movieapplication.databinding.FragmentMovieBinding
import com.petter.movieapplication.ui.detail.DetailActivity
import com.petter.movieapplication.utils.MOVIE_TYPE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MovieFragment : Fragment() {
    private lateinit var movieType: MovieType
    private lateinit var binding: FragmentMovieBinding
    private val viewModel: MainViewModel by viewModels()
    private val topRateAdapter: MainMovieAdapter by lazy { MainMovieAdapter(this::openDetail) }
    private val popularAdapter: MainMovieAdapter by lazy { MainMovieAdapter(this::openDetail) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieType = MovieType.valueOf(it.getString(MOVIE_TYPE, MovieType.MOVIES.name))
        }
        observeFlows()
        viewModel.fetchMovies(movieType)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        binding.movieTopRatedRecyclerView.adapter = topRateAdapter
        binding.moviePopularRecyclerView.adapter = popularAdapter
        return binding.root
    }

    private fun observeFlows() {
        lifecycleScope.launchWhenStarted {
            viewModel.topRateMovieListSateFlow.collect {
                topRateAdapter.items = it
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.popularMovieListStateFlow.collect {
                popularAdapter.items = it
            }
        }

        lifecycleScope.launchWhenStarted {

        }
        lifecycleScope.launchWhenStarted {
            viewModel.moviesLoadingSateFlow.collect {
                binding.movieShimmerTopRate.visibility = it
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.showMoviesSateFlow.collect {
                with(binding) {
                    moviePopular.visibility = it
                    movieTopRated.visibility = it
                }
            }
        }
    }

    private fun openDetail(movie: Movie) {
        DetailActivity.start(requireContext(), movie.id, movieType)
    }

    companion object {
        @JvmStatic
        fun newInstance(movieType: MovieType) =
            MovieFragment().apply {
                arguments = Bundle().apply {
                    putString(MOVIE_TYPE, movieType.name)
                }
            }
    }
}