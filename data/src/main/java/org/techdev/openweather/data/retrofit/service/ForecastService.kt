package org.techdev.openweather.data.retrofit.service

import androidx.lifecycle.MutableLiveData
import org.techdev.openweather.data.repository.ForecastResult
import retrofit2.http.GET

interface ForecastService {

    @GET
    fun getForecast() : MutableLiveData<ForecastResult>


}
