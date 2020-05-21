package org.techdev.openweather.forecast.data.repository

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    val cnt: Int,
    @SerializedName("list") val forecasts: List<Forecast>,
    val city: City
)

data class Forecast(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind,
    val dt_txt: String //Time of data forecasted, ISO, UTC
)

data class Main(
    val temp: String,
    val feels_like: String,
    val temp_min: String,
    val temp_max: String,
    val pressure: String,
    val humidity: String
)
data class Weather(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String)

data class Wind(
    val speed: Float,
    val deg: Int
)

data class City(
    val id: String,
    val name: String,
    val latLng: LatLng,
    val country: String,
    val population: Int)