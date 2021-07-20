package com.petter.datasource.model.response

import com.google.gson.annotations.SerializedName

class MovieResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("vote_count") var voteCount: Int? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null
)