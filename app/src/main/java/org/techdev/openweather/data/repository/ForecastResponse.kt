package org.techdev.openweather.data.repository

data class ForecastResult(val city: City, val list: List<Forecast>)

data class City(
    val id: String,
    val name: String,
    val coordinates: Coordinates,
    val country: String,
    val population: Int)

data class Coordinates(
    val long: Long,
    val lat: String)

data class Forecast(
    val dt: Long,
    val temp: Temperature,
    val pressure: String,
    val humidity: String,
    val weather: List<Weather>,
    val speed: Float,
    val deg: Int,
    val clouds: Int,
    val rain: Float)

data class Temperature(
    val day: Float,
    val min: Float,
    val max: Float,
    val night: Float,
    val evening: Float,
    val morning: Float
)

data class Weather(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String)