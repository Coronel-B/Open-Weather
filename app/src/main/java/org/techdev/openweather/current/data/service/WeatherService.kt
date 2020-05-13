package org.techdev.openweather.current.data.service

import org.techdev.openweather.current.data.repository.WeatherResponse
import retrofit2.http.GET

interface WeatherService {

    @GET("weather?appid=d6842bc2ee8ad484e20158713143979d&id=3430234")
    suspend fun getWeather(
    ) : WeatherResponse

}

