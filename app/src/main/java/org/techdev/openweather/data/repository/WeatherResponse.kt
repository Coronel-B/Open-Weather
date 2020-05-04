package org.techdev.openweather.data.repository

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("id") @Expose val id: Int,
    val name: String,
    val cod: Int,
    val coord: Coordinates2,
    val weather: List<Weather2>,
    val main: Main
)

data class Coordinates2(
    val long: Long,
    val lat: String)

data class Weather2(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String)

data class Main(
    val temp: String,
    val feels_like: String,
    val temp_min: String,
    val temp_max: String,
    val pressure: String,
    val humidity: String
)