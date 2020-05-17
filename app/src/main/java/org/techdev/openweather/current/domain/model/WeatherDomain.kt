package org.techdev.openweather.current.domain.model

import org.techdev.openweather.map.domain.Geolocation

data class WeatherCurrent(
    val id: Int,
    val city: String,
    val geolocation: Geolocation,
    val description: String,
    val icon: String,
    val temp: String
)