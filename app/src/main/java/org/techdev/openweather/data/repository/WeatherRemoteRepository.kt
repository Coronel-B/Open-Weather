package org.techdev.openweather.data.repository

import org.techdev.openweather.data.retrofit.RetrofitService
import org.techdev.openweather.data.retrofit.service.APICallManager
import org.techdev.openweather.util.RemoteErrorEmitter
import org.techdev.openweather.data.retrofit.service.WeatherService

class WeatherRemoteRepository(private val apiCallManager: APICallManager) : WeatherRepository.RemoteRepository {

    var client = RetrofitService.createService(WeatherService::class.java)

    override suspend fun getWeather(remoteErrorEmitter: RemoteErrorEmitter): WeatherResponse? {
        return apiCallManager.executeSafeApiCall(remoteErrorEmitter) {
            client.getWeather()
        }
    }

}