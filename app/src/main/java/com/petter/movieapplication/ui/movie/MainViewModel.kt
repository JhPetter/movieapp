package com.petter.movieapplication.ui.movie

import android.view.View
import androidx.lifecycle.ViewModel
import com.petter.entities.MovieCategory
import com.petter.entities.MovieType
import com.petter.movieapplication.viewobjects.MovieVO
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
    private val _movieVOStateFlow: MutableStateFlow<MovieVO> by lazy {
        MutableStateFlow(MovieVO(arrayListOf(), arrayListOf()))
    }
    val movieVOStateFlow: StateFlow<MovieVO> get() = _movieVOStateFlow

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val _moviesLoadingStateFlow: MutableStateFlow<Int> by lazy {
        MutableStateFlow(View.VISIBLE)
    }
    val moviesLoadingSateFlow: StateFlow<Int> get() = _moviesLoadingStateFlow

    private val _showMoviesStateFlow: MutableStateFlow<Int> by lazy {
        MutableStateFlow(View.GONE)
    }
    val showMoviesSateFlow: StateFlow<Int> get() = _showMoviesStateFlow

    private fun fetchMovies(movieType: MovieType): Single<MovieVO> {
        return Single.zip(
            movieUseCase.fetchMovies(movieType, MovieCategory.POPULAR),
            movieUseCase.fetchMovies(movieType, MovieCategory.TOP_RATE),
            { popularMovies, topRatedMovies ->
                MovieVO(popularMovies.movies, topRatedMovies.movies)
            })
    }

    fun fetchMovieObject(movieType: MovieType) {
        _moviesLoadingStateFlow.value = View.VISIBLE
        _showMoviesStateFlow.value = View.GONE
        val single = fetchMovies(movieType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _movieVOStateFlow.value = it
                    _moviesLoadingStateFlow.value = View.GONE
                    _showMoviesStateFlow.value = View.VISIBLE
                },
                onError = {
                    _moviesLoadingStateFlow.value = View.GONE
                    _showMoviesStateFlow.value = View.GONE
                }
            )
        compositeDisposable.add(single)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}