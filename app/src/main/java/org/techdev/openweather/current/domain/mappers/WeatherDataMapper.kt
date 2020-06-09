package org.techdev.openweather.current.domain.mappers

import com.google.android.gms.maps.model.LatLng
import org.techdev.openweather.current.data.repository.WeatherResponse
import org.techdev.openweather.current.domain.model.WeatherCurrent
import org.techdev.openweather.location.domain.Geolocation

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
            response.city,
            Geolocation(LatLng(
                response.coord.lat.toDouble(),
                response.coord.long.toDouble())),
            response.weather[0].description,
            response.weather[0].icon,
            response.main.temp
        )

}