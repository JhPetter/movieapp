package com.petter.datasource.source

import com.petter.datasource.service.ApiService
import com.petter.entities.Movie
import com.petter.usecases.repository.IMoviesServiceRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MovieService @Inject constructor(private val iApiService: ApiService) :
    IMoviesServiceRepository {
    override fun fetchMovies(): Single<List<Movie>> {
        return iApiService.fetchMovies()
    }
}