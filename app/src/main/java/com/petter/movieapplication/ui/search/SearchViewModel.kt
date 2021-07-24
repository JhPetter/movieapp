package com.petter.movieapplication.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.petter.entities.Movie
import com.petter.entities.MoviePage
import com.petter.entities.MovieType
import com.petter.movieapplication.ui.BaseViewModel
import com.petter.usecases.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : BaseViewModel() {

    private val _myMoviesLiveData: MutableLiveData<List<Movie>> by lazy { MutableLiveData<List<Movie>>() }
    val myMoviesLiveData: LiveData<List<Movie>> get() = _myMoviesLiveData

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var movieType: MovieType = MovieType.MOVIES
    private var lastMoviePage: MoviePage? = null
    private var query: String = ""

    private var mutableList: MutableList<Movie> = mutableListOf()

    fun setMovieType(movieType: MovieType) {
        this.movieType = movieType
    }

    fun loadNextPage() {
        val page = (lastMoviePage?.page ?: 1) + 1
        if (page <= lastMoviePage?.totalPages ?: 1)
            search(query, movieType, page)
    }

    fun search(query: String) {
        this.query = query
        clearData()
        search(query, movieType, 1)
    }

    private fun search(query: String, movieType: MovieType, page: Int) {
        if (query.isEmpty()) {
            clearData()
            return
        }
        val single = movieUseCase.searchMovies(movieType, query, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    lastMoviePage = it
                    mutableList.addAll(it.movies)
                    _myMoviesLiveData.postValue(mutableList)
                },
                onError = {
                    fetchError(it)
                }
            )
        compositeDisposable.add(single)
    }

    private fun clearData() {
        lastMoviePage = null
        mutableList.clear()
        _myMoviesLiveData.postValue(mutableList)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}