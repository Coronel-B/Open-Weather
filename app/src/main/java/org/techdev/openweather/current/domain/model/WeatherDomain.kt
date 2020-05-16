package org.techdev.openweather.current.domain.model

import com.google.android.gms.maps.model.LatLng
import org.techdev.openweather.current.data.repository.Main
import org.techdev.openweather.current.data.repository.Wind
import org.techdev.openweather.map.domain.Geolocation

data class WeatherCurrent(
    val id: Int,
    val city: String,
    val codeCountry: String,
    val geolocation: Geolocation,
    val description: String,
    val icon: String,
    val temp: String
)

data class WeatherCurrentDetail(
    val weatherCurrent: WeatherCurrent,
    val main: Main,
    val wind: Wind
)

