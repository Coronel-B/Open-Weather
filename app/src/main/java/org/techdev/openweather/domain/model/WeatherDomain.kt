package org.techdev.openweather.domain.model

data class WeatherCurrent(
    val id: Int,
    val main: String,
    val description: String,
    val coord: Coordinates
)

data class Coordinates(
    val long: Long,
    val lat: String
)
