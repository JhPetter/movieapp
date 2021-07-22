package com.petter.movieapplication.utils

import android.content.Context
import android.text.format.DateUtils
import java.util.*

fun Date.formatMonthDayYear(context: Context): String {
    return DateUtils.formatDateTime(context, this.time, DateUtils.FORMAT_ABBREV_MONTH);
}