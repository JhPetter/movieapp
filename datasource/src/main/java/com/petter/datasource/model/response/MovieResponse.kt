package com.petter.datasource.model.response

import com.google.gson.annotations.SerializedName

class MovieResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("title", alternate = ["name"]) var title: String? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("vote_count") var voteCount: Int? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null,
    @SerializedName("genres") var genres: List<MovieGenresResponse>? = null,
    @SerializedName("overview") var summary: String? = null,
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("backdrop_path") var backdropPath: String? = null
)

class MovieGenresResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null
)