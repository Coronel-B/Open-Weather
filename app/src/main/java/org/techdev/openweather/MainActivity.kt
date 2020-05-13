package org.techdev.openweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.techdev.openweather.current.ui.WeatherCurrentFragment
import org.techdev.openweather.forecast.ui.ForecastListFragment
import org.techdev.openweather.map.vm.GeolocationVM

class MainActivity : AppCompatActivity() {

    private var _geolocationVM: GeolocationVM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weatherCurrent = WeatherCurrentFragment(
            locationViewModelProvider()
        )
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.weather_current_container, weatherCurrent)
            .commit()

        val forecastListFragment = ForecastListFragment(
            locationViewModelProvider()
        )
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.forecasts_container, forecastListFragment)
            .commit()
    }

    /**
     * PRE: El permiso de ubicación está habilitado
     */
    private fun locationViewModelProvider(): GeolocationVM {
        if (_geolocationVM == null) {
            _geolocationVM = GeolocationVM(this)
            Log.d("TEST", "MainActivity")
        }
        return _geolocationVM!!
    }

}