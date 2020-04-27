package org.techdev.openweather.data.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpClientBuilder {

        private var _okHttpClientBuilder: OkHttpClient.Builder? = null

        val builder: OkHttpClient.Builder
            get() {
                if (_okHttpClientBuilder == null) {
                    val httpLoggingInterceptor = HttpLoggingInterceptor()
                    _okHttpClientBuilder = OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .followRedirects(false)
                        .addInterceptor(httpLoggingInterceptor.apply {
                            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
                        })
                        .addInterceptor(httpLoggingInterceptor.apply {
                            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                        })
                }
                return _okHttpClientBuilder!!
            }

}