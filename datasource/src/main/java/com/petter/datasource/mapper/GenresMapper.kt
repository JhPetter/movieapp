package com.petter.datasource.mapper

import com.petter.datasource.model.response.MovieGenresResponse
import javax.inject.Inject

class GenresMapper @Inject constructor() : Function1<List<MovieGenresResponse>, List<String>> {
    override fun invoke(p1: List<MovieGenresResponse>): List<String> {
        return p1.map { it.name ?: "" }
    }
}