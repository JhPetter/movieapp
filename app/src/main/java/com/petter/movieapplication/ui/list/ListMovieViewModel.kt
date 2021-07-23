package com.petter.movieapplication.ui.list

import androidx.lifecycle.ViewModel
import com.petter.entities.Movie
import com.petter.entities.MovieCategory
import com.petter.entities.MovieType
import com.petter.usecases.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ListMovieViewModel @Inject constructor(private val moviesUseCase: MovieUseCase) :
    ViewModel() {
    private var movies: List<Movie> = arrayListOf()
    private var movieCategory: MovieCategory = MovieCategory.POPULAR
    private var movieType: MovieType = MovieType.TV
    private val _popularMovieListStateFlow: MutableStateFlow<List<Movie>> by lazy {
        MutableStateFlow(arrayListOf())
    }
    val popularMovieListStateFlow: StateFlow<List<Movie>> get() = _popularMovieListStateFlow

    fun config(movies: List<Movie>, movieCategory: MovieCategory, movieType: MovieType) {
        this.movies = movies
        this.movieCategory = movieCategory
        this.movieType = movieType
        _popularMovieListStateFlow.value = movies
    }

    fun fetchMovies() {

    }
}