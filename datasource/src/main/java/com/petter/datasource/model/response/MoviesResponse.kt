package com.petter.datasource.model.response

import com.google.gson.annotations.SerializedName

class MoviesResponse(
    @SerializedName("page") var page: Int? = null,
    @SerializedName("results") var movies: List<MovieResponse>? = null,
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("total_results") var results: Int? = null
)