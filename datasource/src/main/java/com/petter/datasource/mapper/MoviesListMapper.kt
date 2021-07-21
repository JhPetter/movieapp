package com.petter.datasource.mapper

import com.petter.datasource.model.response.MovieResponse
import com.petter.entities.Movie
import javax.inject.Inject

class MoviesListMapper @Inject constructor() : Function1<List<MovieResponse>, List<Movie>> {
    override fun invoke(response: List<MovieResponse>): List<Movie> {
        return response.map {
            Movie(
                id = it.id ?: 0,
                title = it.title ?: "",
                posterPath = it.posterPath ?: "",
                voteCount = it.voteCount ?: 0,
                voteAverage = it.voteAverage ?: 0.0
            )
        }
    }
}