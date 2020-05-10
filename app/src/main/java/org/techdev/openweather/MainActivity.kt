package org.techdev.openweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.techdev.openweather.current.ui.WeatherCurrentFragment
import org.techdev.openweather.forecast.ui.ForecastListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weatherCurrent = WeatherCurrentFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.weather_current_container, weatherCurrent)
            .commit()

        val forecastListFragment = ForecastListFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.forecasts_container, forecastListFragment)
            .commit()
    }
}