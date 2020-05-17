package org.techdev.openweather.forecast.domain.model

data class ForecastList(
    private val forecasts: List<Forecast>
)

data class Forecast(
    val main: Main,
    val weather: Weather,
    val wind: Wind,
    val dt_text: String //Time of data forecasted, ISO, UTC
)

data class Main(
    val temp: String,
    val temp_min: String,
    val temp_max: String,
    val pressure: String,
    val humidity: String
)
data class Weather(
    val main: String,
    val description: String,
    val icon: String)

data class Wind(
    val speed: Float,
    val deg: Int
)