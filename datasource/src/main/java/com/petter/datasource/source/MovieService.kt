package com.petter.datasource.source

import com.petter.datasource.mapper.MoviesMapper
import com.petter.datasource.service.ApiService
import com.petter.entities.Movie
import com.petter.usecases.repository.IMoviesServiceRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MovieService @Inject constructor(
    private val iApiService: ApiService,
    private val moviesMapper: MoviesMapper
) : IMoviesServiceRepository {

    override fun fetchMovies(): Single<List<Movie>> {
        return iApiService.fetchMovies().map { moviesMapper.invoke(it) }
            .onErrorResumeNext { Single.error(Exception("Its local exception")) }
    }
}