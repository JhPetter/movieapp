package com.petter.usecases.usecase

import com.petter.entities.MoviePage
import com.petter.entities.MovieType
import com.petter.usecases.repository.IMoviesServiceRepository
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SearchMovieUseCaseShould {
    private val iMovieServiceRepository: IMoviesServiceRepository = mock()
    private val query: String = ""
    private val moviePage: MoviePage = mock()
    private val movieType: MovieType = mock()
    private val page: Int = 1
    private val runtimeException = RuntimeException("Its local exception")

    @Test
    fun searchMoviesFromServiceSuccess() {
        val movieUseCase = mockSuccess()
        movieUseCase.searchMovies(movieType, query, page).test()
            .assertComplete()
            .assertNoErrors()
        verify(iMovieServiceRepository, times(1)).searchMovies(movieType, query, page)
    }

    @Test
    fun propagateError() {
        val movieUseCase = mockError()
        movieUseCase.searchMovies(movieType, query, page).test()
            .assertError { it.message == runtimeException.message }
            .assertNotComplete()
    }

    private fun mockSuccess(): MovieUseCase {
        whenever(
            iMovieServiceRepository.searchMovies(
                movieType,
                query,
                page
            )
        ).thenReturn(Single.just(moviePage))
        return MovieUseCase(iMovieServiceRepository)
    }

    private fun mockError(): MovieUseCase {
        whenever(iMovieServiceRepository.searchMovies(movieType, query, page)).thenReturn(
            Single.error(runtimeException)
        )
        return MovieUseCase(iMovieServiceRepository)
    }
}