package com.petter.datasource.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings


fun Context?.isConnected(): Boolean {
    return this?.let {
        val cm = it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.getNetworkCapabilities(cm.activeNetwork)
            ?.hasCapability((NetworkCapabilities.NET_CAPABILITY_INTERNET)) ?: false
    } ?: false
}

fun Context?.isAirplaneModeActive(): Boolean {
    return this?.let {
        return Settings.Global.getInt(it.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    } ?: false
}