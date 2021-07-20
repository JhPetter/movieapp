package com.petter.datasource.service

import com.petter.entities.Movie
import io.reactivex.rxjava3.core.Single

interface ApiService {

    fun fetchMovies(): Single<List<Movie>>
}