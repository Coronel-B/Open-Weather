package org.techdev.openweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import org.techdev.openweather.current.ui.WeatherCurrentFragment
import org.techdev.openweather.forecast.ui.ForecastListFragment
import org.techdev.openweather.map.vm.GeolocationVM

class MainActivity : AppCompatActivity() {

    private var geolocationVM: GeolocationVM? = null

    private var weatherCurrentFragment: WeatherCurrentFragment? = null
    private var forecastListFragment: ForecastListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.weather_current_container,  weatherCurrentFragmentProvider())
            .commit()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.forecasts_container, forecastListFragmentProvider())
            .commit()
    }

    /**
     * PRE: El permiso de ubicación está habilitado
     */
    private fun locationViewModelProvider(): GeolocationVM {
        if (geolocationVM == null) {
            geolocationVM = GeolocationVM(this)
        }
        return geolocationVM!!
    }

    private fun weatherCurrentFragmentProvider(): Fragment {
        if (weatherCurrentFragment == null) {
            weatherCurrentFragment = WeatherCurrentFragment(
                locationViewModelProvider()
            )
        }
        return weatherCurrentFragment as WeatherCurrentFragment
    }

    private fun forecastListFragmentProvider(): Fragment {
        if (forecastListFragment == null) {
            forecastListFragment = ForecastListFragment(
                locationViewModelProvider()
            )
        }
        return forecastListFragment as ForecastListFragment
    }

}