package com.petter.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class MoviePage(var page: Int, var movies: List<Movie>, var totalPages: Int) : Parcelable