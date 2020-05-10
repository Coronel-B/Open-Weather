package org.techdev.openweather.current.vm

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import org.techdev.openweather.current.ui.WeatherCurrentFragment
import org.techdev.openweather.data.repository.WeatherRemoteRepository
import org.techdev.openweather.data.repository.WeatherRepositoryImpl
import org.techdev.openweather.data.retrofit.service.APICallManager
import org.techdev.openweather.domain.model.WeatherCurrent
import org.techdev.openweather.map.LocationMapsActivity
import org.techdev.openweather.util.OWViewModel
import org.techdev.openweather.util.RemoteErrorEmitter
import org.techdev.openweather.util.ScreenState
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class WeatherCurrentVM : OWViewModel(),RemoteErrorEmitter {

    private val weatherRemoteRepository= WeatherRemoteRepository(APICallManager())
    private val weatherRepositoryImpl = WeatherRepositoryImpl(weatherRemoteRepository)

    private val _weahter = MutableLiveData<WeatherCurrent>()
    val weather: LiveData<WeatherCurrent> = _weahter

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val _currentLocation = MutableLiveData<Location>()
    val currentLocation: LiveData<Location> = _currentLocation


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

    /**
     * PRO: Describe la temperatura en grados celcius
     * @param kelvin: grados kelvin
     */
    fun convertToDegreeCelcius(kelvin: Double): String {
        val df = DecimalFormat("##.#")
        df.roundingMode = RoundingMode.CEILING
        return df.format(kelvin - 273.15).toString()
    }


    /**
     * PRO: Describe el dia de la semana
     * Source:
     *  . https://stackoverflow.com/a/58820990/5279996
     *  . https://stackoverflow.com/a/29576873/5279996
     */
    fun dayOfWeek(): String {
        val calendar = Calendar.getInstance()
        val date = calendar.time
        val spanishLocale = Locale("es", "ES");
        val dayOfWeek = SimpleDateFormat("EEEE", spanishLocale).format(date.time)
        return dayOfWeek.capitalize()
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

    fun getFusedLocationProviderClient(activity: Activity) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                _currentLocation.value = it
            }
    }

}