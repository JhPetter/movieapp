package com.petter.datasource.source

import com.petter.datasource.mapper.MoviePageMapper
import com.petter.datasource.service.ApiService
import com.petter.entities.MovieCategory
import com.petter.entities.MoviePage
import com.petter.usecases.repository.IMoviesServiceRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MovieService @Inject constructor(
    private val iApiService: ApiService,
    private val moviePageMapper: MoviePageMapper
) : IMoviesServiceRepository {

    override fun fetchMovies(movieCategory: MovieCategory): Single<MoviePage> {
        val response = iApiService.fetchMovies(movieCategory.name)
        return response
            .map {
                moviePageMapper.invoke(it)
            }
            .onErrorResumeNext { Single.error(Exception("Its local exception")) }
    }
}