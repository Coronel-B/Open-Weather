package org.techdev.openweather.domain.model

import org.techdev.openweather.domain.Forecast

class ForecastList(
    val city: String,
    val country: String,
    private val dailyForecast: List<Forecast>) {







}