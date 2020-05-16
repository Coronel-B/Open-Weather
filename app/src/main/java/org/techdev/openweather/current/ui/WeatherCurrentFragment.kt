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
import com.google.android.gms.maps.model.LatLng
import org.techdev.openweather.current.vm.WeatherCurrentVM
import org.techdev.openweather.databinding.FragmentCurrentWeatherBinding
import org.techdev.openweather.current.domain.model.WeatherCurrent
import org.techdev.openweather.extensions.*
import org.techdev.openweather.map.domain.Geolocation
import org.techdev.openweather.map.ui.LocationMapsActivity
import org.techdev.openweather.map.vm.GeolocationVM
import org.techdev.openweather.util.ScreenState
import pub.devrel.easypermissions.EasyPermissions

/**
 * A simple [Fragment] subclass.
 */
class WeatherCurrentFragment(private val geolocationVM: GeolocationVM) : Fragment(),
    EasyPermissions.PermissionCallbacks {

    private lateinit var binding: FragmentCurrentWeatherBinding

    private lateinit var weatherVM: WeatherCurrentVM

    private var geolocationPicked: Geolocation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!checkLocationPermission()) {
            requestLocationPermission()
        }
        if (!checkLocationProviderEnabled()) {
            requestEnableLocationProvider()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//          Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCurrentWeatherBinding.inflate(layoutInflater)

        weatherVM = ViewModelProvider(this).get()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupObservers()
    }


    private fun setupObservers() {
        weatherVM.mutableScreenState.observe(viewLifecycleOwner, Observer {
            binding.weatherCurrentProgressBar.visibility = if (it == ScreenState.LOADING) View.VISIBLE else View.GONE
        })

//        Cuando no hay ninguna ubicación seleccionada
        geolocationVM.currentFusedLocation.observe(viewLifecycleOwner, Observer { geolocation: Geolocation ->
//            TODO: Tirar el request para obtener el clima solo cuando se inicia la app y cada vez que no llega algo en la seleccion del mapa
            getWeatherCurrent(geolocation)
        })

        weatherVM.geolocationPicked.observe(viewLifecycleOwner, Observer { geolocation: Geolocation? ->
            geolocation?.let { getWeatherCurrent(it) }
        })

        weatherVM.weather.observe(viewLifecycleOwner, Observer { weather ->
            Log.d("TEST", "" + weather?.toString())
            bindWeatherCurrent(weather)
        })
    }

    private fun bindWeatherCurrent(weather: WeatherCurrent) {
        binding.imageCurrentWeather.loadFromUrl(
            getIconUrl(weather.icon),
            binding.imgCurrentWeatherProgressBar)

        binding.locationName.text = weather.city
        binding.changeLocation.visibility = VISIBLE

        binding.temp.text = weatherVM.convertToDegreeCelcius(weather.temp.toDouble())
        binding.measurementUnit.visibility = VISIBLE

        binding.dayOfWeek.text = weatherVM.dayOfWeek()
        binding.description.text = weather.description

        binding.changeLocation.setOnClickListener {
            weatherVM.showOriginLocalityPickerScreen(this@WeatherCurrentFragment)
        }
    }

    private fun getWeatherCurrent(geolocation: Geolocation) {
        weatherVM.getWeatherCurrent(geolocation)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            REQUEST_PICK_LOCATION -> {
                if (resultCode == Activity.RESULT_OK && ::binding.isInitialized) {
                    geolocationPicked = data?.getParcelableExtra(LocationMapsActivity.EXTRA_LOCATION)
                    weatherVM.setGeolocationPicked(geolocationPicked)
                }
                return
            }
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d("TEST", "onPermissionsGranted")
        if (checkLocationPermission() && checkLocationProviderEnabled()) {
            geolocationVM.updateCurrentFusedLocation()
        }
    }

    /**
     * PRO: Si se denega el permiso de ubicación se inicializa un hardcodeo
     */
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.d("TEST", "onPermissionsDenied")
        if (!checkLocationPermission() || !checkLocationProviderEnabled()) {
            geolocationVM.setCurrentFusedLocation(Geolocation(LatLng(-30.0, -60.0)))
        }
    }

    companion object {
        //    Identifica la petición hacia el contexto (fragmento) del mapa
        const val REQUEST_PICK_LOCATION = 1
    }

}
