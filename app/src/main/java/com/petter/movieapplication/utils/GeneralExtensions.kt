package com.petter.movieapplication.utils

import android.view.LayoutInflater
import android.widget.ImageView
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.petter.movieapplication.R

fun ImageView.loadImage(url: String) {
    if (url.isNotEmpty()) {
        val imgUri = url.toUri().buildUpon().scheme("https").build()
        Glide.with(this.context)
            .asBitmap()
            .load(imgUri)
            .into(this)
    }
}

fun ChipGroup.addChips(layoutInflater: LayoutInflater, values: List<String>) {
    this.removeAllViews()
    for (item in values) {
        val chip =
            (layoutInflater.inflate(
                R.layout.custom_chip_layout,
                this,
                false
            ) as Chip).apply {
                text = item
            }
        addView(chip)
    }
}