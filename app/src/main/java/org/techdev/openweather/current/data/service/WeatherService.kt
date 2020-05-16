package org.techdev.openweather.current.data.service

import org.techdev.openweather.BuildConfig
import org.techdev.openweather.current.data.repository.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather?appid=d6842bc2ee8ad484e20158713143979d&id=3430234")
    suspend fun getWeather3(
    ) : WeatherResponse

    @GET("weather")
    suspend fun getWeather2(
        @Query("appid") appid: String = BuildConfig.APP_ID,
        @Query("lat") latitude: String,
        @Query("lon") longitude: String
    ) : WeatherResponse

}

