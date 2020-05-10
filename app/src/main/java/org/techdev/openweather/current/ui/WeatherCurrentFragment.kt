package org.techdev.openweather.current.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get

import org.techdev.openweather.current.vm.WeatherCurrentVM
import org.techdev.openweather.databinding.FragmentCurrentWeatherBinding
import org.techdev.openweather.domain.model.WeatherCurrent
import org.techdev.openweather.extensions.getIconUrl
import org.techdev.openweather.extensions.loadFromUrl
import org.techdev.openweather.util.ScreenState
import java.time.DayOfWeek

/**
 * A simple [Fragment] subclass.
 */
class WeatherCurrentFragment : Fragment() {

    private lateinit var binding: FragmentCurrentWeatherBinding

    private lateinit var viewModel: WeatherCurrentVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCurrentWeatherBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this).get()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getWeatherCurrent()
        setupObservers()
    }


    private fun setupObservers() {
        viewModel.mutableScreenState.observe(this, Observer {
            binding.weatherCurrentProgressBar.visibility = if (it == ScreenState.LOADING) View.VISIBLE else View.GONE
        })

        viewModel.weather.observe(this, Observer { weather ->
            Log.d("TEST", "" + weather?.toString())

            bind(weather)
        })
    }

    fun bind(weather: WeatherCurrent) {
        binding.imageCurrentWeather.loadFromUrl(
            getIconUrl(weather.icon),
            binding.imgCurrentWeatherProgressBar)

        binding.temp.text = viewModel.convertToDegreeCelcius(weather.temp.toDouble())
        binding.measurementUnit.visibility = VISIBLE

        binding.locationName.text = weather.city + ", "+ weather.codeCountry    //TODO: Tiene que ser la seleccionada por el usuario
        binding.changeLocation.visibility = VISIBLE

        binding.dayOfWeek.text = viewModel.dayOfWeek()
        binding.description.text = weather.description
    }

    private fun getWeatherCurrent() {
        viewModel.getWeatherCurrent()
    }


}
