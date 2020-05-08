package org.techdev.openweather.extensions

import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import org.techdev.openweather.BuildConfig

/**
 * PRO: Describe la url de un ícono
 * @param: 10n, 13d
 * Source: https://openweathermap.org/weather-conditions
 */
fun Fragment.getIconUrl(icon: String): String {
    val baseImageUrl = BuildConfig.BASE_IMG_URL
    val extension = "@2x.png"
    val pathAppId = "?appid=${BuildConfig.APP_ID}"
    return baseImageUrl + icon + extension + pathAppId
}