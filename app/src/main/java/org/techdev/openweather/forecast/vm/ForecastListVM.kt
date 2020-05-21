package org.techdev.openweather.forecast.vm

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.techdev.openweather.data.retrofit.service.APICallManager
import org.techdev.openweather.forecast.data.repository.ForecastRemoteRepository
import org.techdev.openweather.forecast.data.repository.ForecastRepositoryImpl
import org.techdev.openweather.forecast.domain.model.ForecastList
import org.techdev.openweather.map.domain.Geolocation
import org.techdev.openweather.util.OWViewModel
import org.techdev.openweather.util.RemoteErrorEmitter
import org.techdev.openweather.util.ScreenState

class ForecastListVM : OWViewModel(), RemoteErrorEmitter {

    private val forecastListRemoteRepository = ForecastRemoteRepository(APICallManager())
    private val forecastRepositoryImpl = ForecastRepositoryImpl(forecastListRemoteRepository)

    private val _forecasts = MutableLiveData<ForecastList>()
    val forecasts: LiveData<ForecastList> get() = _forecasts

    fun getForecasts(geolocation: Geolocation) {
        viewModelScope.launch {
            mutableScreenState.postValue(ScreenState.LOADING)

            val forecast = forecastRepositoryImpl.getForecasts(this@ForecastListVM, geolocation)
            _forecasts.postValue(forecast)

            val newState = if (forecast == null) ScreenState.ERROR else ScreenState.RENDER

            mutableScreenState.postValue(newState)
        }
    }


}