package org.techdev.openweather.current.data.repository

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("name") @Expose val city: String,
    @SerializedName("cod") @Expose val codeStatus: Int,
    val timezone: Int,
    val coord: CoordinatesResponse,
    val weather: List<WeatherItem>,
    val base: String,
    val main: Main,
    val visibility: String,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Int,
    val sys: Sys
)

data class CoordinatesResponse(
    val long: Long,
    val lat: String)

data class WeatherItem(
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

data class Wind(
    val speed: Double,
    val deg: Int
)

data class Clouds(
    val all: Int
)

data class Sys(
    val tpye: Int,
    val id: Int,
    val country: String,
    val sunrise: Int,
    val sunset: Int
)