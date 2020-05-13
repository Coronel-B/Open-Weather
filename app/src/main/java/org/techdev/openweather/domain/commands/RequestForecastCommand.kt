package org.techdev.openweather.domain.commands

import org.techdev.openweather.forecast.domain.model.ForecastList

class RequestForecastCommand(private val zipCode: String) :
    Command<ForecastList> {

    override fun execute(): ForecastList {
        /*val forecastRequest =
            ForecastRequest(zipCode)
        return ForecastDataMapper()
            .convertFromDataModel(forecastRequest.execute())*/
        return TODO()
    }


}