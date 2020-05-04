package org.techdev.openweather.list.vm

import androidx.lifecycle.*
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.techdev.openweather.data.repository.*
import org.techdev.openweather.data.retrofit.service.*
import org.techdev.openweather.domain.model.WeatherCurrent
import org.techdev.openweather.util.ErrorType
import org.techdev.openweather.util.OWViewModel
import org.techdev.openweather.util.RemoteErrorEmitter
import org.techdev.openweather.util.ScreenState

class ForecastListVM : OWViewModel(),
    RemoteErrorEmitter {

    /*private val _forecastResult = MutableLiveData<ForecastResult>()
    val forecastResult: LiveData<ForecastResult> get() = _forecastResult*/
    private val _forecastResult = MutableLiveData<JsonObject>()
    val forecastResult: LiveData<JsonObject> get() = _forecastResult

}