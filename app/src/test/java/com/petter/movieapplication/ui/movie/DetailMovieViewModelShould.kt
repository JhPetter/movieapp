package com.petter.movieapplication.ui.movie

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.petter.entities.Movie
import com.petter.entities.MovieType
import com.petter.movieapplication.ui.detail.DetailMovieViewModel
import com.petter.movieapplication.util.RxImmediateSchedulerRule
import com.petter.usecases.usecase.MovieUseCase
import io.reactivex.rxjava3.core.Single
import junit.framework.TestCase.assertEquals
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class DetailMovieViewModelShould {
    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val movieUseCase: MovieUseCase = mock()
    private val movieId = 1
    private val movieType: MovieType = mock()
    private val movieResult: Movie = mock()

    @Test
    fun getDetailMovieFromUseCase() {
        val viewModel = mockSuccess()
        viewModel.fetchMovieById(movieId, movieType)
        verify(movieUseCase, times(1)).fetchMovie(movieId, movieType)
    }

    @Test
    fun emitsDetailMovieFromUseCase() {
        val viewModel = mockSuccess()
        viewModel.fetchMovieById(movieId, movieType)
        assertEquals(movieResult, viewModel.movieLiveData.value)
    }

    @Test
    fun showAndHideShimmerWhileLoading() {
        val viewModel = mockSuccess()
        assertEquals(viewModel.detailLoadingSateFlow.value, View.VISIBLE)
        viewModel.fetchMovieById(movieId, movieType)
        assertEquals(viewModel.detailLoadingSateFlow.value, View.GONE)
    }

    @Test
    fun hideAndShowDataWhileLoading() {
        val viewModel = mockSuccess()
        assertEquals(viewModel.detailDataSateFlow.value, View.GONE)
        viewModel.fetchMovieById(movieId, movieType)
        assertEquals(viewModel.detailDataSateFlow.value, View.VISIBLE)
    }

    private fun mockSuccess(): DetailMovieViewModel {
        whenever(movieUseCase.fetchMovie(movieId, movieType)).thenReturn(Single.just(movieResult))
        return DetailMovieViewModel(movieUseCase)
    }
}