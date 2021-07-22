package com.petter.usecases.repository

import com.petter.entities.Movie
import com.petter.entities.MovieCategory
import com.petter.entities.MoviePage
import com.petter.entities.MovieType
import io.reactivex.rxjava3.core.Single

interface IMoviesServiceRepository {
    fun fetchMovies(movieType: MovieType, movieCategory: MovieCategory): Single<MoviePage>
    fun fetchMovie(movieId: Int): Single<Movie>
    fun searchMovies(query: String): Single<MoviePage>
}