package com.petter.movieapplication.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.petter.entities.Movie
import com.petter.usecases.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase) :
    ViewModel() {

    private val _movieLiveData: MutableLiveData<Movie> by lazy { MutableLiveData() }
    val movieLiveData: LiveData<Movie> get() = _movieLiveData
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var movieId = 0

    fun fetchMovieById(movieId: Int) {
        this.movieId = movieId
        val single = movieUseCase.fetchMovie(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _movieLiveData.postValue(it)
                },
                onError = {
                    println("Here")
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