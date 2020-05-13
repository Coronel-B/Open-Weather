package org.techdev.openweather.forecast.domain.model

data class ForecastList(
    val city: String,
    val country: String,
    private val dailyForecast: List<Forecast>)

data class Forecast(
    val data: String,
    val description: String,
    val high: Int,
    val low: Int
)
