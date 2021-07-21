package com.petter.datasource.source

import com.petter.datasource.mapper.MovieMapper
import com.petter.datasource.mapper.MoviePageMapper
import com.petter.datasource.service.ApiService
import com.petter.entities.Movie
import com.petter.entities.MovieCategory
import com.petter.entities.MoviePage
import com.petter.usecases.repository.IMoviesServiceRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MovieService @Inject constructor(
    private val iApiService: ApiService,
    private val moviePageMapper: MoviePageMapper,
    private val movieMapper: MovieMapper
) : IMoviesServiceRepository {

    override fun fetchMovies(movieCategory: MovieCategory): Single<MoviePage> {
        return iApiService.fetchMovies(movieCategory.name)
            .map { moviePageMapper.invoke(it) }
            .onErrorResumeNext { Single.error(Exception("Its local exception")) }
    }

    override fun fetchMovie(movieId: Int): Single<Movie> {
        return iApiService.fetchMovie(movieId)
            .map { movieMapper.invoke(it) }
            .onErrorResumeNext { Single.error(Exception("Its local exception")) }
    }

    override fun searchMovies(query: String): Single<MoviePage> {
        return iApiService.search(query)
            .map { moviePageMapper.invoke(it) }
            .onErrorResumeNext { Single.error(Exception("Its local exception")) }
    }
}