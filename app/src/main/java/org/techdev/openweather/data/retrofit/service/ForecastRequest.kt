package org.techdev.openweather.data.retrofit.service

import org.techdev.openweather.data.retrofit.RetrofitService

class ForecastRequest(private val zipCode: String) {

    private var client = RetrofitService.createService(ForecastService::class.java)

  /*  fun execute(): MutableLiveData<ForecastResult> {
        val forecast = MutableLiveData<ForecastResult>()

        client.getForecast2().clone().enqueue(object : Callback<ForecastResult> {
            override fun onFailure(call: Call<ForecastResult>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<ForecastResult>,
                response: Response<ForecastResult>
            ) {
            }

        })
        return forecast


    }
*/


}