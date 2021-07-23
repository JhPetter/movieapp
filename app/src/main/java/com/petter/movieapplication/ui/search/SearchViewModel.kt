package com.petter.movieapplication.ui.search

import androidx.lifecycle.ViewModel
import com.petter.entities.Movie
import com.petter.entities.MovieType
import com.petter.usecases.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {
    private val _moviesListStateFlow: MutableStateFlow<List<Movie>> by lazy {
        MutableStateFlow(
            arrayListOf()
        )
    }
    val movieListStateFlow: StateFlow<List<Movie>> get() = _moviesListStateFlow
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun search(query: String, movieType: MovieType) {
        val single = movieUseCase.searchMovies(movieType, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _moviesListStateFlow.value = it.movies
                },
                onError = {

                }
            )
        compositeDisposable.add(single)
    }
}