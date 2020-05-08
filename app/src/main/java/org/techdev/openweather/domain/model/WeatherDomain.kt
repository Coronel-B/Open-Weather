package org.techdev.openweather.domain.model

import org.techdev.openweather.data.repository.CoordinatesResponse
import org.techdev.openweather.data.repository.Main
import org.techdev.openweather.data.repository.Wind

data class WeatherCurrent(
    val id: Int,
    val city: String,
    val codeCountry: String,
    val coordinates: CoordinatesResponse,
    val description: String,
    val icon: String,
    val temp: String
)

data class WeatherCurrentDetail(
    val weatherCurrent: WeatherCurrent,
    val main: Main,
    val wind: Wind
)

