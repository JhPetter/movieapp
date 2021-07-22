package com.petter.datasource.source

import com.petter.datasource.mapper.GenresMapper
import com.petter.datasource.mapper.MovieMapper
import com.petter.datasource.mapper.MoviePageMapper
import com.petter.datasource.mapper.MoviesListMapper
import com.petter.datasource.model.response.MovieResponse
import com.petter.datasource.service.ApiService
import com.petter.entities.MovieType
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MovieServiceShouldTest {
    private lateinit var movieService: MovieService
    private val apiService: ApiService = mock()
    private val moviesListMapper: MoviesListMapper = mock()
    private val genreMapper: GenresMapper = mock()
    private val movieMapper: MovieMapper = MovieMapper(genreMapper)
    private val moviePageMapper: MoviePageMapper = MoviePageMapper(moviesListMapper)

    private val movieType: MovieType = mock()
    private val movieId: Int = 1

    private val runtimeException = RuntimeException("Its local exception")
    private val movieResponse: MovieResponse = MovieResponse(1, "My movie", "path", 1, 1.8)

    @Test
    fun fetchMovieFromApi() {
        mockSuccessCase()
        movieService.fetchMovie(movieId, movieType)
        verify(apiService, times(1)).fetchMovie(movieType.key, movieId)
    }

    @Test
    fun fetchMovieFromApiSuccessful() {
        mockSuccessCase()
        val result = movieService.fetchMovie(movieId, movieType).test()
        result.assertComplete()
            .assertNoErrors()
    }

    @Test
    fun propagateErrors() {
        val result = mockErrorCase()
        val single = result.fetchMovie(movieId, movieType).test()
        single
            .assertError { it.message == runtimeException.message }
            .assertNotComplete()
    }

    @Test
    fun delegateDataToMapper() {
        whenever(
            apiService.fetchMovie(
                movieType.key,
                movieId
            )
        ).thenReturn(Single.just(movieResponse))
        val movieMapper: MovieMapper = mock()
        val service = MovieService(apiService, moviePageMapper, movieMapper)
        service.fetchMovie(movieId, movieType).test()
        verify(movieMapper, times(1)).invoke(movieResponse)
    }

    private fun mockSuccessCase() {
        whenever(
            apiService.fetchMovie(
                movieType.key,
                movieId
            )
        ).thenReturn(Single.just(movieResponse))
        movieService = MovieService(apiService, moviePageMapper, movieMapper)
    }

    private fun mockErrorCase(): MovieService {
        whenever(apiService.fetchMovie(movieType.key, movieId)).thenReturn(
            Single.error(runtimeException)
        )
        return MovieService(apiService, moviePageMapper, movieMapper)
    }
}