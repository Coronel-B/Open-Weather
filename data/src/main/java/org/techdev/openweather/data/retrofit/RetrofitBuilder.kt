package org.techdev.openweather.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private var _retrofitBuilder: Retrofit.Builder? = null

    val builder: Retrofit.Builder
        get() {
            if (_retrofitBuilder == null) {
                _retrofitBuilder = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
            }
            return _retrofitBuilder!!
        }

}