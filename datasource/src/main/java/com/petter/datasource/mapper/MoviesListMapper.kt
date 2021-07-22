package com.petter.datasource.mapper

import com.petter.datasource.model.response.MovieResponse
import com.petter.datasource.util.toDate
import com.petter.entities.Movie
import javax.inject.Inject

class MoviesListMapper @Inject constructor(private val movieMapper: MovieMapper) :
    Function1<List<MovieResponse>, List<Movie>> {
    override fun invoke(response: List<MovieResponse>): List<Movie> {
        return response.map { movieMapper.invoke(it) }
    }
}