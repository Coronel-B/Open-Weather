package org.techdev.openweather.domain.mappers

import org.techdev.openweather.data.repository.WeatherResponse
import org.techdev.openweather.domain.model.Coordinates
import org.techdev.openweather.domain.model.WeatherCurrent

/**
 * Maps the data to the domain model
 */
class WeatherDataMapper {

    /**
     * Convert the weather from the data to the domain model
     */
    fun convertFromDataModelToDomain(response: WeatherResponse) : WeatherCurrent =
        WeatherCurrent(
            response.id,
            response.weather[0].main,
            response.weather[0].description,
            Coordinates(
                response.coord.long,
                response.coord.lat
            )
        )

}