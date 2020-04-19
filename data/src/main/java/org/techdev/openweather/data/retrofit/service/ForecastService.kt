package org.techdev.openweather.data.retrofit.service

import retrofit2.http.GET

interface ForecastService {

    @GET
    fun getForecast()


}
