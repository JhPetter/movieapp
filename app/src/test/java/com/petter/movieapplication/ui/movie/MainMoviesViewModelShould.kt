package com.petter.movieapplication.ui.movie

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.petter.entities.Movie
import com.petter.entities.MovieCategory
import com.petter.entities.MoviePage
import com.petter.entities.MovieType
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
    private val mockMovies: MoviePage = MoviePage(
        1,
        arrayListOf(
            Movie(
                1,
                "My Title",
                "summary",
                posterPath = "url",
                voteCount = 1,
                voteAverage = 1.1,
                bannerPath = "",
                releaseDate = null
            )
        ),
        10
    )

    private val mockMoviesPopular: MoviePage = MoviePage(
        1,
        arrayListOf(
            Movie(
                2,
                "My Title",
                "summary",
                posterPath = "url",
                voteCount = 1,
                voteAverage = 1.1,
                bannerPath = "",
                releaseDate = null
            )
        ),
        10
    )


    @Test
    fun getMoviesTopRateFromUseCase() {
        val viewModel = mockSuccess()
        viewModel.fetchMovieObject(movieType)
        verify(movieUseCase, times(1)).fetchMovies(movieType, MovieCategory.TOP_RATE)
        verify(movieUseCase, times(1)).fetchMovies(movieType, MovieCategory.POPULAR)
    }

    @Test
    fun emitsMoviesTopRateFromUseCase() {
        val viewModel = mockSuccess()
        viewModel.fetchMovieObject(movieType)
        assertEquals(mockMovies.movies, viewModel.movieVOStateFlow.value.topRateMovies)
        assertEquals(mockMoviesPopular.movies, viewModel.movieVOStateFlow.value.popularMovies)
    }

    @Test
    fun showAndHideShimmerWhileLoading() {
        val viewModel = mockSuccess()
        assertEquals(viewModel.moviesLoadingSateFlow.value, View.VISIBLE)
        viewModel.fetchMovieObject(movieType)
        assertEquals(viewModel.moviesLoadingSateFlow.value, View.GONE)
    }

    @Test
    fun hideAndShowDataWhileLoading() {
        val viewModel = mockSuccess()
        assertEquals(viewModel.showMoviesSateFlow.value, View.GONE)
        viewModel.fetchMovieObject(movieType)
        assertEquals(viewModel.showMoviesSateFlow.value, View.VISIBLE)
    }

    private fun mockSuccess(): MainViewModel {
        whenever(movieUseCase.fetchMovies(movieType, MovieCategory.TOP_RATE)).thenReturn(
            Single.just(mockMovies)
        )
        whenever(movieUseCase.fetchMovies(movieType, MovieCategory.POPULAR)).thenReturn(
            Single.just(mockMoviesPopular)
        )
        return MainViewModel(movieUseCase)
    }
}