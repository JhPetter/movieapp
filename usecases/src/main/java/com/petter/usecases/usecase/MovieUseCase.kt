package com.petter.usecases.usecase

import com.petter.entities.MovieCategory
import com.petter.entities.MoviePage
import com.petter.entities.MovieType
import com.petter.usecases.repository.IMoviesServiceRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MovieUseCase @Inject constructor(private val iMoviesServiceRepository: IMoviesServiceRepository) {
    fun fetchMovies(movieType: MovieType, movieCategory: MovieCategory): Single<MoviePage> =
        iMoviesServiceRepository.fetchMovies(movieType, movieCategory)

    fun fetchMovie(movieId: Int,movieType: MovieType) = iMoviesServiceRepository.fetchMovie(movieId,movieType)

    fun searchMovies(query: String) = iMoviesServiceRepository.searchMovies(query)
}