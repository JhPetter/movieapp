package com.petter.datasource.mapper

import com.petter.datasource.model.response.MoviesResponse
import com.petter.entities.MoviePage
import javax.inject.Inject

class MoviePageMapper @Inject constructor(private val moviesMapper: MoviesMapper) :
    Function1<MoviesResponse, MoviePage> {
    override fun invoke(response: MoviesResponse): MoviePage {
        return MoviePage(
            response.page ?: 0,
            moviesMapper.invoke(response.movies ?: arrayListOf()),
            response.totalPages ?: 0
        )
    }
}