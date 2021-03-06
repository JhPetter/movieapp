package com.petter.datasource.service

import com.petter.datasource.model.response.MovieResponse
import com.petter.datasource.model.response.MoviesResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("{movieType}/{category}")
    fun fetchMovies(
        @Path("movieType") movieType: String,
        @Path("category") category: String,
        @Query("page") page: Int
    ): Single<MoviesResponse>

    @GET("{movieType}/{movieId}")
    fun fetchMovie(
        @Path("movieType") movieType: String,
        @Path("movieId") movieId: Int
    ): Single<MovieResponse>

    @GET("search/{movieType}")
    fun search(
        @Path("movieType") movieType: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Single<MoviesResponse>
}