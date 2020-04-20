package org.techdev.openweather.data.retrofit

import retrofit2.create

class Request {

    /**
     * PRO: Read result of response
     */
    fun run() {

//        TODO: Use coroutine


    }


    /**
     * PRO: Read result of response
     */
    fun <T> buildService(originalService: Class<T>): T {
        val okHttpClient = OkHttpClientBuilder.builder.build()
        val retrofitBuilder = RetrofitBuilder.builder
        return retrofitBuilder
            .baseUrl("")
            .client(okHttpClient)
            .build()
            .create()


    }

}