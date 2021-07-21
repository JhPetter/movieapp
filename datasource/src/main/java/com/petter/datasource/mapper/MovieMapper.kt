package com.petter.datasource.mapper

import com.petter.datasource.model.response.MovieResponse
import com.petter.entities.Movie
import javax.inject.Inject

class MovieMapper @Inject constructor() : Function1<MovieResponse, Movie> {
    override fun invoke(p1: MovieResponse): Movie {
        return Movie(
            id = p1.id ?: 0,
            title = p1.title ?: "",
            posterPath = p1.posterPath ?: "",
            voteCount = p1.voteCount ?: 0,
            voteAverage = p1.voteAverage ?: 0.0
        )
    }
}