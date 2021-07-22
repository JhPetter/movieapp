package com.petter.movieapplication.ui.detail

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.petter.entities.Movie
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
class DetailMovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase) :
    ViewModel() {

    private val _movieLiveData: MutableLiveData<Movie> by lazy { MutableLiveData() }
    val movieLiveData: LiveData<Movie> get() = _movieLiveData
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var movieId = 0
    private val _detailLoadingStateFlow: MutableStateFlow<Int> by lazy {
        MutableStateFlow(View.VISIBLE)
    }
    val detailLoadingSateFlow: StateFlow<Int> get() = _detailLoadingStateFlow

    private val _detailDataStateFlow: MutableStateFlow<Int> by lazy {
        MutableStateFlow(View.VISIBLE)
    }
    val detailDataSateFlow: StateFlow<Int> get() = _detailDataStateFlow


    fun fetchMovieById(movieId: Int, movieType: MovieType) {
        this.movieId = movieId
        _detailLoadingStateFlow.value = View.VISIBLE
        _detailDataStateFlow.value = View.GONE
        val single = movieUseCase.fetchMovie(movieId, movieType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _movieLiveData.postValue(it)
                    _detailLoadingStateFlow.value = View.GONE
                    _detailDataStateFlow.value = View.VISIBLE
                },
                onError = {
                    println(it.stackTrace)
                    _detailLoadingStateFlow.value = View.GONE
                    _detailDataStateFlow.value = View.VISIBLE
                }
            )

        single.addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}