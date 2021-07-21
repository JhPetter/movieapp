package com.petter.usecases.usecase

import com.petter.entities.Movie
import com.petter.usecases.repository.IMoviesServiceRepository
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MovieUseCaseShould {
    private val iMovieServiceRepository: IMoviesServiceRepository = mock()
    private val movieId: Int = 1
    private val movie: Movie = mock()
    private val runtimeException = RuntimeException("Its local exception")

    @Test
    fun fetchMovieByIdFromServiceSuccess() {
        val movieUseCase = mockSuccess()
        movieUseCase.fetchMovie(movieId).test()
            .assertComplete()
            .assertNoErrors()
        verify(iMovieServiceRepository, times(1)).fetchMovie(movieId)
    }

    @Test
    fun propagateError() {
        val movieUseCase = mockError()
        movieUseCase.fetchMovie(movieId).test()
            .assertError { it.message == runtimeException.message }
            .assertNotComplete()
    }

    private fun mockSuccess(): MovieUseCase {
        whenever(iMovieServiceRepository.fetchMovie(movieId)).thenReturn(Single.just(movie))
        return MovieUseCase(iMovieServiceRepository)
    }

    private fun mockError(): MovieUseCase {
        whenever(iMovieServiceRepository.fetchMovie(movieId)).thenReturn(
            Single.error(runtimeException)
        )
        return MovieUseCase(iMovieServiceRepository)
    }
}