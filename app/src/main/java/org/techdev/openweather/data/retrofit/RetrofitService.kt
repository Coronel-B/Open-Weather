package org.techdev.openweather.data.retrofit

object RetrofitService {

    /**
     * PRO: Read result of response
     */
    fun <T> createService(originalService: Class<T>): T {
        val okHttpClient = OkHttpClientBuilder.builder.build()

        return RetrofitBuilder.builder
            .client(okHttpClient)
            .build()
            .create(originalService)

    }

}