package com.petter.datasource.service

import com.petter.datasource.model.response.MoviesResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("movie/{category}")
    fun fetchMovies(@Path("category") category: String): Single<MoviesResponse>
}