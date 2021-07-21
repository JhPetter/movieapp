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

class SearchMovieServiceShouldTest {
    private lateinit var movieService: MovieService
    private val apiService: ApiService = mock()
    private val moviesListMapper: MoviesListMapper = mock()
    private val movieMapper: MovieMapper = mock()
    private val moviePageMapper: MoviePageMapper = MoviePageMapper(moviesListMapper)

    private val query: String = "mine"
    private val runtimeException = RuntimeException("Its local exception")
    private val moviesResponse: MoviesResponse = MoviesResponse(1, arrayListOf(), 10, 20)

    @Test
    fun fetchMoviesFromApi() {
        mockSuccessCase()
        movieService.searchMovies(query)
        verify(apiService, times(1)).search(query)
    }

    @Test
    fun fetchMoviesFromApiSuccessful() {
        mockSuccessCase()
        val result = movieService.searchMovies(query).test()
        result.assertComplete()
            .assertNoErrors()
    }

    @Test
    fun propagateErrors() {
        val result = mockErrorCase()
        val single = result.searchMovies(query).test()
        single
            .assertError { it.message == runtimeException.message }
            .assertNotComplete()
    }

    @Test
    fun delegateDataToMapper() {
        whenever(apiService.search(query)).thenReturn(Single.just(moviesResponse))
        val moviePagerMapper: MoviePageMapper = mock()
        val service = MovieService(apiService, moviePagerMapper, movieMapper)
        service.searchMovies(query).test()
        verify(moviePagerMapper, times(1)).invoke(moviesResponse)
    }

    private fun mockSuccessCase() {
        whenever(apiService.search(query)).thenReturn(Single.just(moviesResponse))
        movieService = MovieService(apiService, moviePageMapper, movieMapper)
    }

    private fun mockErrorCase(): MovieService {
        whenever(apiService.search(query)).thenReturn(
            Single.error(runtimeException)
        )
        return MovieService(apiService, moviePageMapper, movieMapper)
    }
}