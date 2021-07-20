package com.petter.usecases.repository

import com.petter.entities.Movie
import io.reactivex.rxjava3.core.Single

interface IMoviesServiceRepository {
    fun fetchMovies(): Single<List<Movie>>
}