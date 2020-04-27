package org.techdev.openweather.data.retrofit

import org.techdev.openweather.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private var _retrofitBuilder: Retrofit.Builder? = null

    val builder: Retrofit.Builder
        get() {
            if (_retrofitBuilder == null) {
                _retrofitBuilder = Retrofit.Builder()
//                    .baseUrl(BuildConfig.BASE_URL)    //TODO habilitar
                    .baseUrl("https://samples.openweathermap.org/data/2.5/")
                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(LiveDataCallAdapterFactory())
            }
            return _retrofitBuilder!!
        }

}