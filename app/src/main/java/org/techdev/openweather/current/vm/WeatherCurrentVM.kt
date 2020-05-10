package org.techdev.openweather.current.vm

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.techdev.openweather.BuildConfig
import org.techdev.openweather.data.repository.WeatherRemoteRepository
import org.techdev.openweather.data.repository.WeatherRepositoryImpl
import org.techdev.openweather.data.retrofit.service.APICallManager
import org.techdev.openweather.domain.model.WeatherCurrent
import org.techdev.openweather.util.OWViewModel
import org.techdev.openweather.util.RemoteErrorEmitter
import org.techdev.openweather.util.ScreenState
import java.math.RoundingMode
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.truncate
import kotlin.reflect.KProperty

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
     * PRO: Describe una ubicacion desde Google Maps
     */
    fun getLocationFromMaps(): String {

        return "Palermo"
    }


}