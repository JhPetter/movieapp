package com.petter.datasource.source

import com.petter.datasource.mapper.MovieMapper
import com.petter.datasource.mapper.MoviePageMapper
import com.petter.datasource.mapper.MoviesListMapper
import com.petter.datasource.model.response.MoviesResponse
import com.petter.datasource.service.ApiService
import com.petter.entities.MovieCategory
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MovieListServiceShouldTest {
    private lateinit var movieService: MovieService
    private val apiService: ApiService = mock()
    private val moviesListMapper: MoviesListMapper = mock()
    private val movieMapper: MovieMapper = mock()
    private val moviePageMapper: MoviePageMapper = MoviePageMapper(moviesListMapper)


    private val movieCategory: MovieCategory = mock()
    private val runtimeException = RuntimeException("Its local exception")
    private val moviesResponse: MoviesResponse = MoviesResponse(1, arrayListOf(), 10, 20)

    @Test
    fun fetchMoviesFromApi() {
        mockSuccessCase()
        movieService.fetchMovies(movieCategory)
        verify(apiService, times(1)).fetchMovies(movieCategory.name)
    }

    @Test
    fun fetchMoviesFromApiSuccessful() {
        mockSuccessCase()
        val result = movieService.fetchMovies(movieCategory).test()
        result.assertComplete()
            .assertNoErrors()
    }

    @Test
    fun propagateErrors() {
        val result = mockErrorCase()
        val single = result.fetchMovies(movieCategory).test()
        single
            .assertError { it.message == runtimeException.message }
            .assertNotComplete()
    }

    @Test
    fun delegateDataToMapper() {
        whenever(apiService.fetchMovies(movieCategory.name)).thenReturn(Single.just(moviesResponse))
        val moviePagerMapper: MoviePageMapper = mock()
        val service = MovieService(apiService, moviePagerMapper, movieMapper)
        service.fetchMovies(movieCategory).test()
        verify(moviePagerMapper, times(1)).invoke(moviesResponse)
    }

    private fun mockSuccessCase() {
        whenever(apiService.fetchMovies(movieCategory.name)).thenReturn(Single.just(moviesResponse))
        movieService = MovieService(apiService, moviePageMapper, movieMapper)
    }

    private fun mockErrorCase(): MovieService {
        whenever(apiService.fetchMovies(movieCategory.name)).thenReturn(
            Single.error(runtimeException)
        )
        return MovieService(apiService, moviePageMapper, movieMapper)
    }
}