package com.petter.movieapplication.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.petter.entities.Movie
import com.petter.entities.MovieCategory
import com.petter.entities.MoviePage
import com.petter.entities.MovieType
import com.petter.usecases.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ListMovieViewModel @Inject constructor(private val moviesUseCase: MovieUseCase) :
    ViewModel() {
    private var movieCategory: MovieCategory = MovieCategory.POPULAR
    private var movieType: MovieType = MovieType.TV
    private val _myMoviesLiveData: MutableLiveData<List<Movie>> by lazy { MutableLiveData<List<Movie>>() }
    val myMoviesLiveData: LiveData<List<Movie>> get() = _myMoviesLiveData
    private var mutableList: MutableList<Movie> = mutableListOf()

    private var lastMoviePage: MoviePage? = null
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun config(movies: List<Movie>, movieCategory: MovieCategory, movieType: MovieType) {
        lastMoviePage = MoviePage(1, movies, 2)
        mutableList.addAll(movies)
        this.movieCategory = movieCategory
        this.movieType = movieType
        _myMoviesLiveData.postValue(mutableList)
    }

    fun loadNextPage() {
        val page = (lastMoviePage?.page ?: 1) + 1
        if (page <= lastMoviePage?.totalPages ?: 1)
            fetchNewData(page)
    }

    private fun fetchNewData(page: Int) {
        val single = moviesUseCase.fetchMovies(movieType, movieCategory, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    lastMoviePage = it
                    mutableList.addAll(it.movies)
                    _myMoviesLiveData.postValue(mutableList)
                },
                onError = {
                    it.printStackTrace()
                }
            )
        compositeDisposable.add(single)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}