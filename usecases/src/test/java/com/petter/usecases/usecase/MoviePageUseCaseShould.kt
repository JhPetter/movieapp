package com.petter.usecases.usecase

import com.petter.entities.MovieCategory
import com.petter.entities.MoviePage
import com.petter.entities.MovieType
import com.petter.usecases.repository.IMoviesServiceRepository
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MoviePageUseCaseShould {
    private val iMovieServiceRepository: IMoviesServiceRepository = mock()
    private val category: MovieCategory = mock()
    private val movieType: MovieType = mock()
    private val moviePage: MoviePage = mock()
    private val page: Int = 1
    private val runtimeException = RuntimeException("Its local exception")

    @Test
    fun fetchMoviesFromServiceSuccess() {
        val movieUseCase = mockSuccess()
        movieUseCase.fetchMovies(movieType, category).test()
            .assertComplete()
            .assertNoErrors()
        verify(iMovieServiceRepository, times(1)).fetchMovies(movieType, category, page)
    }

    @Test
    fun propagateError() {
        val movieUseCase = mockError()
        movieUseCase.fetchMovies(movieType, category).test()
            .assertError { it.message == runtimeException.message }
            .assertNotComplete()
    }

    private fun mockSuccess(): MovieUseCase {
        whenever(iMovieServiceRepository.fetchMovies(movieType, category, page)).thenReturn(
            Single.just(
                moviePage
            )
        )
        return MovieUseCase(iMovieServiceRepository)
    }

    private fun mockError(): MovieUseCase {
        whenever(iMovieServiceRepository.fetchMovies(movieType, category, page)).thenReturn(
            Single.error(runtimeException)
        )
        return MovieUseCase(iMovieServiceRepository)
    }
}