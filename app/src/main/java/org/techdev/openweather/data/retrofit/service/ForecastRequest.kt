package org.techdev.openweather.data.retrofit.service

import androidx.lifecycle.MutableLiveData
import org.techdev.openweather.data.repository.ForecastResult
import org.techdev.openweather.data.retrofit.OkHttpClientBuilder
import org.techdev.openweather.data.retrofit.Request
import org.techdev.openweather.data.retrofit.RetrofitBuilder
import retrofit2.create

class ForecastRequest(private val zipCode: String) {

    companion object {
        private const val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
        private const val URL = "http://api.openweathermap.org/data/2.5/" +
                "forecast/daily?mode=json&units=metric&cnt=7"
        private const val COMPLETE_URL = "$URL&APPID=$APP_ID&zip="
    }


    fun execute(): MutableLiveData<ForecastResult> {
        /*val forecastService = request.buildService(
            ForecastService::class.java
        )
        return forecastService.getForecast()*/
        return TODO()
    }



}