package com.petter.movieapplication.ui.movie

import androidx.lifecycle.ViewModel
import com.petter.entities.Movie
import com.petter.entities.MovieCategory
import com.petter.entities.MovieType
import com.petter.usecases.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {
    private val _popularMovieListLiveData: MutableStateFlow<List<Movie>> by lazy {
        MutableStateFlow(arrayListOf())
    }
    val popularMovieListLiveData: StateFlow<List<Movie>> get() = _popularMovieListLiveData


    private val _topRateMovieListLiveData: MutableStateFlow<List<Movie>> by lazy {
        MutableStateFlow(arrayListOf())
    }
    val topRateMovieListLiveData: StateFlow<List<Movie>> get() = _topRateMovieListLiveData

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val loading: Boolean = false


    fun fetchPopularMovies(movieType: MovieType) {
        val single = movieUseCase.fetchMovies(movieType, MovieCategory.POPULAR)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _popularMovieListLiveData.value = it.movies
                },
                onError = {
                    println("Here")
                    println(it.stackTrace)
                }
            )

        single.addTo(compositeDisposable)
    }

    fun fetchTopRateMovies(movieType: MovieType) {
        val single = movieUseCase.fetchMovies(movieType, MovieCategory.TOP_RATE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _topRateMovieListLiveData.value = it.movies
                },
                onError = {
                    println(it.stackTrace)
                }
            )

        single.addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}