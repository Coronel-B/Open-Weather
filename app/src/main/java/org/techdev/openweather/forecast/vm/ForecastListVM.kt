package org.techdev.openweather.forecast.vm

import androidx.lifecycle.*
import com.google.gson.JsonObject
import org.techdev.openweather.util.OWViewModel
import org.techdev.openweather.util.RemoteErrorEmitter

class ForecastListVM : OWViewModel(),
    RemoteErrorEmitter {

    /*private val _forecastResult = MutableLiveData<ForecastResult>()
    val forecastResult: LiveData<ForecastResult> get() = _forecastResult*/
    private val _forecastResult = MutableLiveData<JsonObject>()
    val forecastResult: LiveData<JsonObject> get() = _forecastResult

}