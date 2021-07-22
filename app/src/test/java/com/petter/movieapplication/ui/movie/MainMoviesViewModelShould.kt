package com.petter.movieapplication.ui.movie

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
    private val movieCategory: MovieCategory = MovieCategory.TOP_RATE
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


    @Test
    fun getMoviesTopRateFromUseCase() {
        val viewModel = mockSuccess()
        viewModel.fetchTopRateMovies(movieType)
        verify(movieUseCase, times(1)).fetchMovies(movieType, movieCategory)
    }

    @Test
    fun emitsMoviesTopRateFromUseCase() {
        val viewModel = mockSuccess()
        viewModel.fetchTopRateMovies(movieType)
        assertEquals(mockMovies.movies, viewModel.topRateMovieListSateFlow.value)
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