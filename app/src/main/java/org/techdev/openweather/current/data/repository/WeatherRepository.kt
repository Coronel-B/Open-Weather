package org.techdev.openweather.current.data.repository

import org.techdev.openweather.util.RemoteErrorEmitter
import org.techdev.openweather.current.domain.model.WeatherCurrent
import org.techdev.openweather.location.domain.Geolocation

interface WeatherRepository {

    suspend fun getWeather(remoteErrorEmitter: RemoteErrorEmitter, geolocation: Geolocation) : WeatherCurrent?

    interface RemoteRepository {
        suspend fun getWeather(remoteErrorEmitter: RemoteErrorEmitter, geolocation: Geolocation) : WeatherResponse?
    }

}