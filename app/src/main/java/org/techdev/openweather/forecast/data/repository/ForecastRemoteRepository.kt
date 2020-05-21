package org.techdev.openweather.forecast.data.repository

import org.techdev.openweather.data.retrofit.RetrofitService
import org.techdev.openweather.data.retrofit.service.APICallManager
import org.techdev.openweather.forecast.data.service.ForecastService
import org.techdev.openweather.map.domain.Geolocation
import org.techdev.openweather.util.RemoteErrorEmitter

class ForecastRemoteRepository(private val apiCallManager: APICallManager) :
    ForecastRepository.RemoteRepository {

    private val forecastService = RetrofitService.createService(ForecastService::class.java)

    override suspend fun getForecasts(
        remoteErrorEmitter: RemoteErrorEmitter,
        geolocation: Geolocation
    ): ForecastResponse? {
        return apiCallManager.executeSafeApiCall(remoteErrorEmitter) {
            forecastService.getForecasts(
                latitude = geolocation.geolocation.latitude.toString(),
                longitude = geolocation.geolocation.longitude.toString()
            )
        }
    }

}