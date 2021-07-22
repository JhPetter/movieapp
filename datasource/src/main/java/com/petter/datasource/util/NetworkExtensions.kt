package com.petter.datasource.util

import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(): Date? {
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)
}