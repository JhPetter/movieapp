package com.petter.movieapplication.utils

import android.widget.ImageView
import androidx.core.net.toUri
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String) {
    if (url.isNotEmpty()) {
        val imgUri = url.toUri().buildUpon().scheme("https").build()
        Glide.with(this.context)
            .asBitmap()
            .load(imgUri)
            .into(this)
    }
}