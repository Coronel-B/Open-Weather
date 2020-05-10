package org.techdev.openweather.current.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import org.techdev.openweather.current.vm.WeatherCurrentVM
import org.techdev.openweather.databinding.FragmentCurrentWeatherBinding
import org.techdev.openweather.domain.model.WeatherCurrent
import org.techdev.openweather.extensions.getIconUrl
import org.techdev.openweather.extensions.loadFromUrl
import org.techdev.openweather.map.LocationMapsActivity
import org.techdev.openweather.util.ScreenState

/**
 * A simple [Fragment] subclass.
 */
class WeatherCurrentFragment : Fragment() {

    private lateinit var binding: FragmentCurrentWeatherBinding

    private lateinit var viewModel: WeatherCurrentVM

    private var originLocalityPicked: String? = null

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

        getFusedLocationProviderClient()
        getWeatherCurrent()
        setupObservers()
    }


    private fun setupObservers() {
        viewModel.mutableScreenState.observe(this, Observer {
            binding.weatherCurrentProgressBar.visibility = if (it == ScreenState.LOADING) View.VISIBLE else View.GONE
        })


        viewModel.weather.observe(this, Observer { weather ->
            Log.d("TEST", "" + weather?.toString())
            bindWeatherCurrent(weather)
        })

    }

    fun bindWeatherCurrent(weather: WeatherCurrent) {
        binding.imageCurrentWeather.loadFromUrl(
            getIconUrl(weather.icon),
            binding.imgCurrentWeatherProgressBar)

        binding.temp.text = viewModel.convertToDegreeCelcius(weather.temp.toDouble())
        binding.measurementUnit.visibility = VISIBLE

//
        viewModel.currentLocation.observe(this, Observer {
            Log.d("TEST", "latitude: " + it.latitude.toString() + ", longitude " + it.longitude.toString())
//            TODO: Tirar el request solo la primera vez y cuando no llega nada en la seleccion del mapa
            binding.locationName.text = "Palermo"
            binding.changeLocation.visibility = VISIBLE
        })


        binding.dayOfWeek.text = viewModel.dayOfWeek()
        binding.description.text = weather.description

        binding.changeLocation.setOnClickListener {
            viewModel.showOriginLocalityPickerScreen(this@WeatherCurrentFragment)
        }
    }

    private fun getWeatherCurrent() {
        viewModel.getWeatherCurrent()
    }

    private fun getFusedLocationProviderClient() {
        activity?.let { viewModel.getFusedLocationProviderClient(it) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            REQUEST_PICK_LOCATION -> {
                if (resultCode == Activity.RESULT_OK && ::binding.isInitialized) {
                    originLocalityPicked =
                        data!!.getStringExtra(LocationMapsActivity.EXTRA_LOCATION)
                    binding.locationName.text = originLocalityPicked        //TODO: Concatenar el codigo de pais de la nueva ubicacion
//                    TODO: Cambiar el clima en base a la nueva ubicacion
                }
                return
            }
        }
    }

    companion object {
        //    Identifica la petición hacia el contexto (fragmento) del mapa
        const val REQUEST_PICK_LOCATION = 1
    }

}
