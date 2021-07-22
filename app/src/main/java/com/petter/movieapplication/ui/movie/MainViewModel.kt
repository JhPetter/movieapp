package com.petter.movieapplication.ui.movie

import android.view.View
import androidx.lifecycle.ViewModel
import com.petter.entities.Movie
import com.petter.entities.MovieCategory
import com.petter.entities.MovieType
import com.petter.usecases.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {
    private val _popularMovieListStateFlow: MutableStateFlow<List<Movie>> by lazy {
        MutableStateFlow(arrayListOf())
    }
    val popularMovieListStateFlow: StateFlow<List<Movie>> get() = _popularMovieListStateFlow
    private val _topRateMovieListStateFlow: MutableStateFlow<List<Movie>> by lazy {
        MutableStateFlow(arrayListOf())
    }
    val topRateMovieListSateFlow: StateFlow<List<Movie>> get() = _topRateMovieListStateFlow
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val _moviesLoadingStateFlow: MutableStateFlow<Int> by lazy {
        MutableStateFlow(View.VISIBLE)
    }
    val moviesLoadingSateFlow: StateFlow<Int> get() = _moviesLoadingStateFlow

    private val _showMoviesStateFlow: MutableStateFlow<Int> by lazy {
        MutableStateFlow(View.VISIBLE)
    }
    val showMoviesSateFlow: StateFlow<Int> get() = _showMoviesStateFlow

    fun fetchMovies(movieType: MovieType) {
        _moviesLoadingStateFlow.value = View.VISIBLE
        _showMoviesStateFlow.value = View.GONE
        Single.zip(
            movieUseCase.fetchMovies(movieType, MovieCategory.POPULAR),
            movieUseCase.fetchMovies(movieType, MovieCategory.TOP_RATE),
            { popularMovies, topRatedMovies ->
                _popularMovieListStateFlow.value = popularMovies.movies
                _topRateMovieListStateFlow.value = topRatedMovies.movies
                _moviesLoadingStateFlow.value = View.GONE
                _showMoviesStateFlow.value = View.VISIBLE
            }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = {
                    _moviesLoadingStateFlow.value = View.GONE
                    _showMoviesStateFlow.value = View.GONE
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}