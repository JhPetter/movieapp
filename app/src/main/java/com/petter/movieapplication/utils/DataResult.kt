package com.petter.movieapplication.utils

sealed class DataResult {
    class Loading(visible: Int) : DataResult()
}