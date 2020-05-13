package org.techdev.openweather.forecast.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import org.techdev.openweather.data.retrofit.RetrofitService
import org.techdev.openweather.forecast.data.service.ForecastService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastRepository {


//    private val forecastService: ForecastService = TODO()

    private var client: ForecastService = RetrofitService.createService(
        ForecastService::class.java)


    /*fun getForecastResult(): MutableLiveData<ForecastResult> {
        return TODO()
    }
*/

    /**
     * simplified version of the retrofit call that comes from support with coroutines
     * Note that this does NOT handle errors, to be added
     */
    fun getForecastResult(): MutableLiveData<JsonObject> {
        val forecast = MutableLiveData<JsonObject>()
        client.getForecast().clone().enqueue(object : Callback<JsonObject>{

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d("ForecastRepository", "onFailure()")
                forecast.value = null
            }

            override fun onResponse(cFall: Call<JsonObject>, response: Response<JsonObject>) {
                Log.d("ForecastRepository", "onResponse()")
                Log.d("TEST", response.body().toString())
                forecast.value = response.body()
            }

        })
        return forecast
    }

    /**
     * OBS: will later be called through a coroutine.
     */
    suspend fun getForecastResult2() = client.getForecast2()


}

