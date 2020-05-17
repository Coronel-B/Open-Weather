package org.techdev.openweather.forecast.data.repository

import org.techdev.openweather.forecast.domain.mappers.ForecastDataMapper
import org.techdev.openweather.forecast.domain.model.ForecastList
import org.techdev.openweather.map.domain.Geolocation
import org.techdev.openweather.util.RemoteErrorEmitter

class ForecastRepositoryImpl(private val remoteRepository: ForecastRepository.RemoteRepository) :
    ForecastRepository {

    override suspend fun getForecasts(remoteErrorEmitter: RemoteErrorEmitter, geolocation: Geolocation): ForecastList? {
        val response = remoteRepository.getForecasts(remoteErrorEmitter, geolocation)
        val mapper = ForecastDataMapper()
        return response?.let {
            mapper.convertFromDataModelToDomain(it)
        }
    }

}

