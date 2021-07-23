package com.petter.movieapplication.viewobjects

import com.petter.entities.Movie

data class MovieVO(val popularMovies: List<Movie>, val topRateMovies: List<Movie>)