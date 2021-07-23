package com.petter.movieapplication.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.petter.entities.Movie
import com.petter.entities.MovieCategory
import com.petter.entities.MovieType
import com.petter.movieapplication.databinding.FragmentMovieBinding
import com.petter.movieapplication.ui.detail.DetailActivity
import com.petter.movieapplication.ui.list.ListMovieActivity
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
        viewModel.fetchMovieObject(movieType)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        setUpView()
        return binding.root
    }

    private fun setUpView() {
        with(binding) {
            movieTopRatedRecyclerView.adapter = topRateAdapter
            moviePopularRecyclerView.adapter = popularAdapter
            movieSeeAllTopRated.setOnClickListener {
                openFullList(
                    ArrayList(viewModel.movieVOStateFlow.value.topRateMovies),
                    MovieCategory.TOP_RATE,
                    movieType
                )
            }
            movieSeeAllPopular.setOnClickListener {
                openFullList(
                    ArrayList(viewModel.movieVOStateFlow.value.popularMovies),
                    MovieCategory.POPULAR,
                    movieType
                )
            }
        }
    }

    private fun observeFlows() {
        lifecycleScope.launchWhenStarted {
            viewModel.movieVOStateFlow.collect {
                popularAdapter.items = it.popularMovies
                topRateAdapter.items = it.topRateMovies
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
                    movieSeeAllPopular.visibility = it
                    movieSeeAllTopRated.visibility = it
                }
            }
        }
    }

    private fun openDetail(movie: Movie) {
        DetailActivity.start(requireContext(), movie.id, movieType)
    }

    private fun openFullList(movies: ArrayList<Movie>, category: MovieCategory, type: MovieType) {
        ListMovieActivity.start(
            requireContext(),
            movies,
            category,
            type
        )
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