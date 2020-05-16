package org.techdev.openweather.current.data.repository

import org.techdev.openweather.data.retrofit.RetrofitService
import org.techdev.openweather.data.retrofit.service.APICallManager
import org.techdev.openweather.util.RemoteErrorEmitter
import org.techdev.openweather.current.data.service.WeatherService
import org.techdev.openweather.map.domain.Geolocation

class WeatherRemoteRepository(private val apiCallManager: APICallManager) :
    WeatherRepository.RemoteRepository {

    var client = RetrofitService.createService(WeatherService::class.java)

    override suspend fun getWeather(remoteErrorEmitter: RemoteErrorEmitter, geolocation: Geolocation): WeatherResponse? {
        return apiCallManager.executeSafeApiCall(remoteErrorEmitter) {
            client.getWeather2(
                latitude = geolocation.geolocation.latitude.toString(),
                longitude = geolocation.geolocation.longitude.toString())
        }
    }

}