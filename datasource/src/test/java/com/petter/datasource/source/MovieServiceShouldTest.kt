package com.petter.datasource.source

import com.petter.datasource.mapper.MoviesMapper
import com.petter.datasource.model.response.MovieResponse
import com.petter.datasource.service.ApiService
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MovieServiceShouldTest {
    private lateinit var movieService: MovieService
    private val apiService: ApiService = mock()
    private val mapper: MoviesMapper = mock()
    private val runtimeException = RuntimeException("Its local exception")
    private val listMovies:List<MovieResponse> = mock()

    @Test
    fun fetchMoviesFromApi() {
        mockSuccessCase()
        movieService.fetchMovies()
        verify(apiService, times(1)).fetchMovies()
    }

    @Test
    fun fetchMoviesFromApiSuccessful() {
        mockSuccessCase()
        val result = movieService.fetchMovies().test()
        result.assertComplete()
            .assertNoErrors()
    }

    @Test
    fun propagateErrors() {
        val result = mockErrorCase()
        val single = result.fetchMovies().test()
        single
            .assertError { it.message == runtimeException.message }
            .assertNotComplete()
    }

    @Test
    fun delegateBusinessLogicToMapper() {
        mockSuccessCase()
        movieService.fetchMovies().test()
        verify(mapper, times(1)).invoke(listMovies)
    }

    private fun mockSuccessCase() {
        whenever(apiService.fetchMovies()).thenReturn(Single.just(listMovies))
        movieService = MovieService(apiService, mapper)
    }

    private fun mockErrorCase(): MovieService {
        whenever(apiService.fetchMovies()).thenReturn(Single.error(runtimeException))
        return MovieService(apiService, mapper)
    }
}