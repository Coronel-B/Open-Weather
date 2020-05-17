package org.techdev.openweather.forecast.domain.mappers

import org.techdev.openweather.forecast.data.repository.Forecast
import org.techdev.openweather.forecast.data.repository.ForecastResponse
import org.techdev.openweather.forecast.domain.model.ForecastList
import org.techdev.openweather.forecast.domain.model.Main
import org.techdev.openweather.forecast.domain.model.Weather
import org.techdev.openweather.forecast.domain.model.Wind
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import org.techdev.openweather.forecast.domain.model.Forecast as ModelForecast

/**
 * Maps the data to the domain model
 */
class ForecastDataMapper {

    fun convertFromDataModelToDomain(forecast: ForecastResponse) : ForecastList =
        ForecastList(
            convertForecastListToDomain(forecast.forecasts)
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
            Main(
                forecast.main.temp,
                forecast.main.temp_min,
                forecast.main.temp_max,
                forecast.main.pressure,
                forecast.main.humidity
            ),
            Weather(
                forecast.weather[0].main,
                forecast.weather[0].description,
                forecast.weather[0].icon
            ),
            Wind(
                forecast.wind.speed,
                forecast.wind.deg
            ),
            dt_text = convertDate(forecast.dt)  //TODO: forecast.dt_text
        )
    }

    private fun convertDate(date: Long): String {
        val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return dateFormat.format(date)
    }

}