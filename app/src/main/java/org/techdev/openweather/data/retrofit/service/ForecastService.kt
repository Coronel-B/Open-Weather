package org.techdev.openweather.data.retrofit.service

import com.google.gson.JsonObject
import org.techdev.openweather.data.repository.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET

interface ForecastService {

    @GET("forecast?q=M%C3%BCnchen,DE&appid=439d4b804bc8187953eb36d2a8c26a02")
    fun getForecast(
    ) : Call<JsonObject>
//    ) : MutableLiveData<String>
//    fun getForecast() : MutableLiveData<ForecastResult>

    @GET("weather?appid=d6842bc2ee8ad484e20158713143979d&id=3430234")
    suspend fun getForecast2(
    ) : Call<WeatherResponse>

    @GET("weather?appid=d6842bc2ee8ad484e20158713143979d&id=3430234")
    suspend fun getForecast3(
    ) : WeatherResponse

}

