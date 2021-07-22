package com.petter.movieapplication.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.petter.entities.MovieCategory
import com.petter.entities.MoviePage
import com.petter.entities.MovieType
import com.petter.movieapplication.util.RxImmediateSchedulerRule
import com.petter.usecases.usecase.MovieUseCase
import io.reactivex.rxjava3.core.Single
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MainMoviesViewModelShould {

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val movieUseCase: MovieUseCase = mock()
    private val movieType: MovieType = mock()
    private val movieCategory: MovieCategory = mock()
    private val mockMovies: MoviePage = mock()


    @Test
    fun getMoviesTopRateFromUseCase() {
        val viewModel = mockSuccess()
        viewModel.fetchTopRateMovies(movieType)
        verify(movieUseCase, times(1)).fetchMovies(movieType, movieCategory)
    }


    private fun mockSuccess(): MainViewModel {
        whenever(movieUseCase.fetchMovies(movieType, movieCategory)).thenReturn(
            Single.just(
                mockMovies
            )
        )
        return MainViewModel(movieUseCase)
    }
}