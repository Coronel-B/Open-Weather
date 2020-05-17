package org.techdev.openweather.current.data.repository

import org.techdev.openweather.util.RemoteErrorEmitter
import org.techdev.openweather.current.domain.mappers.WeatherDataMapper
import org.techdev.openweather.current.domain.model.WeatherCurrent
import org.techdev.openweather.map.domain.Geolocation

class WeatherRepositoryImpl(private val remoteRepository: WeatherRepository.RemoteRepository) :
    WeatherRepository {

    override suspend fun getWeather(remoteErrorEmitter: RemoteErrorEmitter, geolocation: Geolocation) : WeatherCurrent? {
        val response = remoteRepository.getWeather(remoteErrorEmitter, geolocation)
        val mapper = WeatherDataMapper()
        return response?.let {
            mapper.convertFromDataModelToDomain(it)
        }
    }

}