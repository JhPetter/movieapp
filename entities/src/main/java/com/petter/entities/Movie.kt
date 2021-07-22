package com.petter.entities

import java.util.*

class Movie(
    var id: Int,
    var title: String,
    var summary: String,
    var releaseDate: Date?,
    var posterPath: String,
    var voteCount: Int,
    var voteAverage: Double,
    var bannerPath: String = "",
    var genres: List<String> = arrayListOf()
)