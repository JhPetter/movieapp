package com.petter.datasource.source

import com.petter.datasource.mapper.MoviesListMapper
import com.petter.datasource.model.response.MovieResponse
import org.junit.Test
import junit.framework.TestCase.assertEquals

class MovieListMapperShould {
    private val movieResponse = MovieResponse(1, "Ave fenix", "", 1, 1.3)
    private val mapper = MoviesListMapper()
    private val movies = mapper.invoke(arrayListOf(movieResponse))
    private val movie = movies[0]

    @Test
    fun keepSameId() {
        assertEquals(movieResponse.id, movie.id)
    }

    @Test
    fun keepSameTitle() {
        assertEquals(movieResponse.title, movie.title)
    }

    @Test
    fun keepSamePosterPath() {
        assertEquals(movieResponse.posterPath, movie.posterPath)
    }

    @Test
    fun keepSameVoteCount() {
        assertEquals(movieResponse.voteCount, movie.voteCount)
    }

    @Test
    fun keepSameVoteAverage() {
        assertEquals(movieResponse.voteAverage, movie.voteAverage)
    }
}