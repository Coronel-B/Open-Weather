package org.techdev.openweather.forecast.data.service

import com.google.gson.JsonObject
import org.techdev.openweather.BuildConfig
import org.techdev.openweather.current.data.repository.WeatherResponse
import org.techdev.openweather.forecast.data.repository.ForecastResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastService {

    /**
     * OBS: count = 40 is default
     * @param count [1, 40]: 5d / 3h
     */
    @GET("forecast")
    suspend fun getForecasts(
        @Query("appid") appid: String = BuildConfig.APP_ID,
        @Query("cnt") count: Int = 40,
        @Query("lat") latitude: String,
        @Query("lon") longitude: String
    ) : ForecastResponse

}

