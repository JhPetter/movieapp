package com.petter.movieapplication.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.petter.entities.Movie
import com.petter.entities.MovieCategory
import com.petter.entities.MoviePage
import com.petter.entities.MovieType
import com.petter.movieapplication.ui.list.ListMovieViewModel
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

class ListMovieViewModelShould {

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
    private val moviePage: MoviePage = MoviePage(
        2, arrayListOf(
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
        ), 2
    )
    private val listOfMovies: List<Movie> = arrayListOf(
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
    )
    private val page: Int = 2


    @Test
    fun configSameListMovies() {
        val listViewModel = mockSuccess()
        listViewModel.config(listOfMovies, movieCategory, movieType)
        assertEquals(listViewModel.myMoviesLiveData.value?.size, listOfMovies.size)
    }

    @Test
    fun loadNextPageFromUseCase() {
        val listViewModel = mockSuccess()
        listViewModel.config(listOfMovies, movieCategory, movieType)
        listViewModel.loadNextPage()
        verify(movieUseCase, times(1)).fetchMovies(movieType, movieCategory, page)
    }

    @Test
    fun emitsMoviesFromUseCase() {
        val listViewModel = mockSuccess()
        listViewModel.config(listOfMovies, movieCategory, movieType)
        listViewModel.loadNextPage()
        assertEquals(listViewModel.myMoviesLiveData.value?.size, 2)
    }


    private fun mockSuccess(): ListMovieViewModel {
        whenever(movieUseCase.fetchMovies(movieType, movieCategory, page)).thenReturn(
            Single.just(moviePage)
        )
        return ListMovieViewModel(movieUseCase)
    }
}