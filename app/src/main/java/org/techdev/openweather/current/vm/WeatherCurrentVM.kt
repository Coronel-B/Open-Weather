package org.techdev.openweather.current.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.techdev.openweather.data.repository.WeatherRemoteRepository
import org.techdev.openweather.data.repository.WeatherRepositoryImpl
import org.techdev.openweather.data.retrofit.service.APICallManager
import org.techdev.openweather.domain.model.WeatherCurrent
import org.techdev.openweather.util.OWViewModel
import org.techdev.openweather.util.RemoteErrorEmitter
import org.techdev.openweather.util.ScreenState

class WeatherCurrentVM : OWViewModel(),RemoteErrorEmitter {

    private val weatherRemoteRepository= WeatherRemoteRepository(APICallManager())
    private val weatherRepositoryImpl = WeatherRepositoryImpl(weatherRemoteRepository)

    private val _weahter = MutableLiveData<WeatherCurrent>()
    val weather: LiveData<WeatherCurrent> = _weahter

    fun getWeatherCurrent() {
//        Easy way to get off the main thread
        viewModelScope.launch {
            mutableScreenState.postValue(ScreenState.LOADING)

            val weather = weatherRepositoryImpl.getWeather(this@WeatherCurrentVM)
            _weahter.postValue(weather)

            val newState = if (weather == null) ScreenState.ERROR else ScreenState.RENDER

            mutableScreenState.postValue(newState)
        }
    }


}