package org.techdev.openweather.data.retrofit.service

import org.techdev.openweather.data.repository.WeatherResponse
import retrofit2.http.GET

interface WeatherService {

    @GET("weather?appid=d6842bc2ee8ad484e20158713143979d&id=3430234")
    suspend fun getWeather(
    ) : WeatherResponse

}

