package org.techdev.openweather.data.repository

import org.techdev.openweather.util.RemoteErrorEmitter
import org.techdev.openweather.domain.model.WeatherCurrent

interface WeatherRepository {

    suspend fun getWeather(remoteErrorEmitter: RemoteErrorEmitter) : WeatherCurrent?

    interface RemoteRepository {
        suspend fun getWeather(remoteErrorEmitter: RemoteErrorEmitter) : WeatherResponse?
    }

}