package org.techdev.openweather.extensions

import androidx.fragment.app.Fragment
import org.techdev.openweather.BuildConfig
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * PRO: Describe la url de un ícono
 * @param: 10n, 13d
 * Source: https://openweathermap.org/weather-conditions
 */
fun getIconUrl(icon: String): String {
    val baseImageUrl = BuildConfig.BASE_IMG_URL
    val extension = "@2x.png"
    val pathAppId = "?appid=${BuildConfig.APP_ID}"
    return baseImageUrl + icon + extension + pathAppId
}

/**
 * PRO: Describe el dia de la semana
 * Source:
 *  . https://stackoverflow.com/a/58820990/5279996
 *  . https://stackoverflow.com/a/29576873/5279996
 *  . https://stackoverflow.com/a/22195170/5279996
 */
fun dayOfWeek(daysToAdd: Int? = 0): String {
    val calendar = Calendar.getInstance()
    val date = calendar.time
    daysToAdd?.let { calendar.add(Calendar.DAY_OF_WEEK, it) }
    val spanishLocale = Locale("es", "ES")
    val englishLocale = Locale("en", "EN")
    val dayOfWeek = SimpleDateFormat("EEEE", englishLocale).format(date.time)
    return dayOfWeek.capitalize()
}

/**
 * PRO: Describe la temperatura en grados celcius
 * @param kelvin: grados kelvin
 */
fun convertToDegreeCelcius(kelvin: Double): String {
    val df = DecimalFormat("##.#")
    df.roundingMode = RoundingMode.CEILING
    return df.format(kelvin - 273.15).toString()
}