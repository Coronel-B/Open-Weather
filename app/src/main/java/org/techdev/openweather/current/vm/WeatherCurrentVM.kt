package org.techdev.openweather.current.vm

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.techdev.openweather.current.ui.WeatherCurrentFragment
import org.techdev.openweather.current.data.repository.WeatherRemoteRepository
import org.techdev.openweather.current.data.repository.WeatherRepositoryImpl
import org.techdev.openweather.data.retrofit.service.APICallManager
import org.techdev.openweather.current.domain.model.WeatherCurrent
import org.techdev.openweather.map.domain.Geolocation
import org.techdev.openweather.map.ui.LocationMapsActivity
import org.techdev.openweather.util.OWViewModel
import org.techdev.openweather.util.RemoteErrorEmitter
import org.techdev.openweather.util.ScreenState

class WeatherCurrentVM : OWViewModel(), RemoteErrorEmitter {

    private val weatherRemoteRepository = WeatherRemoteRepository(APICallManager())
    private val weatherRepositoryImpl = WeatherRepositoryImpl(weatherRemoteRepository)

    private val _weahter = MutableLiveData<WeatherCurrent>()
    val weather: LiveData<WeatherCurrent> = _weahter

    fun getWeatherCurrent(geolocation: Geolocation) {
//        Easy way to get off the main thread
        viewModelScope.launch {
            mutableScreenState.postValue(ScreenState.LOADING)

            val weather = weatherRepositoryImpl.getWeather(this@WeatherCurrentVM, geolocation)
            _weahter.postValue(weather)

            val newState = if (weather == null) ScreenState.ERROR else ScreenState.RENDER

            mutableScreenState.postValue(newState)
        }
    }

    /**
     * PRO: Inicia el fragmento del mapa.
     * PRE: En el dispositivo Google Play Services está actualizado
     */
    fun showOriginLocalityPickerScreen(context: Fragment) {
        val requestIntent = Intent(context.activity, LocationMapsActivity::class.java)
        requestIntent.action = LocationMapsActivity.ACTION_PICK_LOCATION
        context.startActivityForResult(requestIntent, WeatherCurrentFragment.REQUEST_PICK_LOCATION)
    }

}