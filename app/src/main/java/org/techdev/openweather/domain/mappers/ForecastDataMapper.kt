package org.techdev.openweather.domain.mappers

import org.techdev.openweather.data.repository.Forecast
import org.techdev.openweather.data.repository.ForecastResponse
import org.techdev.openweather.domain.model.ForecastList
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import org.techdev.openweather.domain.model.Forecast as ModelForecast


/**
 * Maps the data to the domain model
 */
class ForecastDataMapper {

    fun convertFromDataModel(forecast: ForecastResponse) : ForecastList =
        ForecastList(
            forecast.city.name,
            forecast.city.country,
            convertForecastListToDomain(forecast.list)
        )

    /**
     * Convert the forecast list from the data to the domain model
     */
    private fun convertForecastListToDomain(list: List<Forecast>) : List<ModelForecast> {
        return list.mapIndexed {i, forecast ->
            val dataTime = Calendar.getInstance().timeInMillis + TimeUnit.DAYS.toMillis(i.toLong())
            convertForecastItemToDomain(forecast.copy(dt = dataTime))
        }
    }

    private fun convertForecastItemToDomain(forecast: Forecast): ModelForecast {
        return ModelForecast(
            convertDate(forecast.dt),
            forecast.weather[0].description,
            forecast.temp.max.toInt(),
            forecast.temp.min.toInt())
    }

    private fun convertDate(date: Long): String {
        val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return dateFormat.format(date)
    }

}