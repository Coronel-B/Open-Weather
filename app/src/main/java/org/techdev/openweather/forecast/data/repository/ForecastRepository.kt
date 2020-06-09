package org.techdev.openweather.forecast.data.repository

import org.techdev.openweather.forecast.domain.model.ForecastList
import org.techdev.openweather.location.domain.Geolocation
import org.techdev.openweather.util.RemoteErrorEmitter

interface ForecastRepository {

    suspend fun getForecasts(remoteErrorEmitter: RemoteErrorEmitter, geolocation: Geolocation)
            : ForecastList?

    interface RemoteRepository {
        suspend fun getForecasts(remoteErrorEmitter: RemoteErrorEmitter, geolocation: Geolocation)
            : ForecastResponse?
    }

}