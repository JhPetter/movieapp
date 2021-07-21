package com.petter.datasource.source

import com.petter.datasource.mapper.MoviePageMapper
import com.petter.datasource.mapper.MoviesMapper
import com.petter.datasource.model.response.MoviesResponse
import junit.framework.TestCase
import org.junit.Test
import org.mockito.kotlin.mock

class MoviePageMapperShould {
    private val moviePageResponse = MoviesResponse(1, arrayListOf(), 10, 20)
    private val movieMapper: MoviesMapper = mock()
    private val mapper = MoviePageMapper(movieMapper)
    private val moviePage = mapper.invoke(moviePageResponse)

    @Test
    fun keepSamePage() {
        TestCase.assertEquals(moviePageResponse.page, moviePage.page)
    }

    @Test
    fun keepSameTitle() {
        TestCase.assertEquals(moviePageResponse.totalPages, moviePage.totalPages)
    }

    @Test
    fun keepSamePosterPath() {
        TestCase.assertEquals(moviePageResponse.movies?.size, moviePage.movies.size)
    }
}