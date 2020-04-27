package org.techdev.openweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.techdev.openweather.list.ui.ForecastListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val forecastListFragment = ForecastListFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, forecastListFragment)
            .commit()

    }
}