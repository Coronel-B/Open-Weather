package org.techdev.openweather.extensions

import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide

fun ImageView.loadFromUrl(url: String, progressBar: ProgressBar) {
    progressBar.visibility = VISIBLE
    Glide.with(context)
        .load(url)
        .centerCrop()
        .placeholder(android.R.drawable.stat_notify_error)
        .into(this)
    progressBar.visibility = GONE
}
