package com.petter.datasource.service

import com.petter.entities.Movie
import io.reactivex.rxjava3.core.Single
import retrofit2.http.POST

interface ApiService {
    @POST("movie/{category}")
    fun fetchMovies(): Single<List<Movie>>
}