package com.petter.usecases.repository

import com.petter.entities.MovieCategory
import com.petter.entities.MoviePage
import io.reactivex.rxjava3.core.Single

interface IMoviesServiceRepository {
    fun fetchMovies(movieCategory: MovieCategory): Single<MoviePage>
}