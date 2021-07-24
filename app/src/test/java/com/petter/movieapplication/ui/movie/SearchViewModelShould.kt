package com.petter.movieapplication.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.petter.entities.Movie
import com.petter.entities.MoviePage
import com.petter.entities.MovieType
import com.petter.movieapplication.ui.search.SearchViewModel
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

class SearchViewModelShould {

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val movieUseCase: MovieUseCase = mock()
    private val query: String = "Marvel"
    private val movieType: MovieType = mock()
    private val page = 1
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
        ), 5
    )

    @Test
    fun searchMoviesFromUseCase() {
        val searchViewModel = mockSuccess()
        searchViewModel.search(query)
        verify(movieUseCase, times(1)).searchMovies(movieType, query, page)
    }

    @Test
    fun searchMoviesPagingFromUseCase() {
        val searchViewModel = mockSuccess()
        searchViewModel.search(query)
        whenever(movieUseCase.searchMovies(movieType, query, 3)).thenReturn(
            Single.just(moviePage)
        )
        searchViewModel.loadNextPage()
        verify(movieUseCase, times(1)).searchMovies(movieType, query, 3)
    }

    @Test
    fun emitMoviesPagingFromUseCase() {
        val searchViewModel = mockSuccess()
        searchViewModel.search(query)
        whenever(movieUseCase.searchMovies(movieType, query, 3)).thenReturn(
            Single.just(moviePage)
        )
        searchViewModel.loadNextPage()
        assertEquals(searchViewModel.myMoviesLiveData.value?.size, 2)
    }

    private fun mockSuccess(): SearchViewModel {
        whenever(movieUseCase.searchMovies(movieType, query, page)).thenReturn(
            Single.just(moviePage)
        )
        val viewModel = SearchViewModel(movieUseCase)
        viewModel.setMovieType(movieType)
        return viewModel
    }
}