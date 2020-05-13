package org.techdev.openweather.current.data.repository

import org.techdev.openweather.util.RemoteErrorEmitter
import org.techdev.openweather.current.domain.mappers.WeatherDataMapper
import org.techdev.openweather.current.domain.model.WeatherCurrent

class WeatherRepositoryImpl(private val remoteRepository: WeatherRepository.RemoteRepository
) : WeatherRepository {

    override suspend fun getWeather(remoteErrorEmitter: RemoteErrorEmitter) : WeatherCurrent? {
        val response = remoteRepository.getWeather(remoteErrorEmitter)
        val mapper =
            WeatherDataMapper()
        return response?.let {
            mapper.convertFromDataModelToDomain(it)
        }
    }

}