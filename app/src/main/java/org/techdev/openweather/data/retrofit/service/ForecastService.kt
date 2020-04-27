package org.techdev.openweather.data.retrofit.service

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface ForecastService {

    @GET("forecast?q=M%C3%BCnchen,DE&appid=439d4b804bc8187953eb36d2a8c26a02")
    fun getForecast(
    ) : Call<JsonObject>
//    ) : MutableLiveData<String>
//    fun getForecast() : MutableLiveData<ForecastResult>

}

