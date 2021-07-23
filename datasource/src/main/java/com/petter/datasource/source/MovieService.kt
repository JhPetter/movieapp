package com.petter.datasource.source

import com.petter.datasource.mapper.MovieMapper
import com.petter.datasource.mapper.MoviePageMapper
import com.petter.datasource.service.ApiService
import com.petter.entities.Movie
import com.petter.entities.MovieCategory
import com.petter.entities.MoviePage
import com.petter.entities.MovieType
import com.petter.usecases.repository.IMoviesServiceRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MovieService @Inject constructor(
    private val iApiService: ApiService,
    private val moviePageMapper: MoviePageMapper,
    private val movieMapper: MovieMapper
) : IMoviesServiceRepository {

    override fun fetchMovies(
        movieType: MovieType,
        movieCategory: MovieCategory
    ): Single<MoviePage> {
        return iApiService.fetchMovies(movieType.key, movieCategory.key)
            .map { moviePageMapper.invoke(it) }
            .onErrorResumeNext { Single.error(Exception("Its local exception")) }
    }

    override fun fetchMovie(movieId: Int, movieType: MovieType): Single<Movie> {
        return iApiService.fetchMovie(movieType.key, movieId)
            .map { movieMapper.invoke(it) }
            .onErrorResumeNext { Single.error(Exception("Its local exception")) }
    }

    override fun searchMovies(movieType: MovieType, query: String): Single<MoviePage> {
        return iApiService.search(movieType.key, query)
            .map { moviePageMapper.invoke(it) }
            .onErrorResumeNext { Single.error(Exception("Its local exception")) }
    }
}