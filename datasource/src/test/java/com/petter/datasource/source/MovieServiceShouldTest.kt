package com.petter.datasource.source

import com.petter.datasource.service.ApiService
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class MovieServiceShouldTest {

    private lateinit var service: MovieService
    private val apiService: ApiService = mock()

    @Test
    fun fetchMoviesFromApi() {
        service = MovieService(apiService)
        service.fetchMovies()
        verify(apiService, times(1)).fetchMovies()
    }
}