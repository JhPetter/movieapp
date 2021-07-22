package com.petter.datasource.service

import com.petter.datasource.model.response.MovieResponse
import com.petter.datasource.model.response.MoviesResponse
import com.petter.entities.MovieType
import io.reactivex.rxjava3.core.Single
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("{movieType}/{category}")
    fun fetchMovies(
        @Path("movieType") movieType: String,
        @Path("category") category: String
    ): Single<MoviesResponse>

    @POST("movie/{movieId}")
    fun fetchMovie(@Path("movieId") movieId: Int): Single<MovieResponse>

    @POST("search/movie")
    fun search(@Query("query") query: String): Single<MoviesResponse>
}