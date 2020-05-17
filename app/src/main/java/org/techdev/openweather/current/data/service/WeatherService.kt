package org.techdev.openweather.current.data.service

import org.techdev.openweather.BuildConfig
import org.techdev.openweather.current.data.repository.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    suspend fun getWeather(
        @Query("appid") appid: String = BuildConfig.APP_ID,
        @Query("lat") latitude: String,
        @Query("lon") longitude: String
    ) : WeatherResponse

}

