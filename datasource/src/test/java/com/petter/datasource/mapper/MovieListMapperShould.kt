package com.petter.datasource.mapper

import com.petter.datasource.model.response.MovieResponse
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock

class MovieListMapperShould {
    private val movieResponse = MovieResponse(1, "Ave fenix", "", 1, 1.3, summary = "My Summary")
    private val genresMapper: GenresMapper = mock()
    private val movieMapper: MovieMapper = MovieMapper(genresMapper)
    private val mapper = MoviesListMapper(movieMapper)
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

    @Test
    fun mapEmptyGenresWhenReturnNull() {
        assertEquals(movieResponse.genres == null, movie.genres.isEmpty())
    }

    @Test
    fun keepSameSummary() {
        assertEquals(movieResponse.summary, movie.summary)
    }
}