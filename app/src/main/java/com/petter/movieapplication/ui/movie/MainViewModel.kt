package com.petter.movieapplication.ui.movie

import androidx.lifecycle.ViewModel
import com.petter.entities.Movie
import com.petter.entities.MovieCategory
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
    private val _movieListLiveData: MutableStateFlow<List<Movie>> by lazy {
        MutableStateFlow(arrayListOf())
    }
    val movieListLiveData: StateFlow<List<Movie>> get() = _movieListLiveData

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun fetchMovies() {
        val single = movieUseCase.fetchMovies(MovieCategory.POPULAR)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _movieListLiveData.value = it.movies
                },
                onError = {

                }
            )

        single.addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}