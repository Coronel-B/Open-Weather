package org.techdev.openweather.forecast.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get

import org.techdev.openweather.databinding.FragmentListForecastBinding
import org.techdev.openweather.extensions.checkLocationPermission
import org.techdev.openweather.extensions.checkLocationProviderEnabled
import org.techdev.openweather.forecast.vm.ForecastListVM
import org.techdev.openweather.location.domain.Geolocation
import org.techdev.openweather.location.vm.GeolocationVM
import org.techdev.openweather.util.ScreenState
import pub.devrel.easypermissions.EasyPermissions

/**
 * PRO: Render a list of daily forecasts for the next 5 days
 */
class ForecastListFragment(private val geolocationVM: GeolocationVM) : Fragment(),
    EasyPermissions.PermissionCallbacks {

    private lateinit var binding: FragmentListForecastBinding
    private lateinit var forecastVM: ForecastListVM
    private lateinit var forecastListAdapter: ForecastListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListForecastBinding.inflate(layoutInflater)

        forecastListAdapter = ForecastListAdapter()
        binding.listRecyclerView.adapter = forecastListAdapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        forecastVM = ViewModelProvider(this).get()

        setupObservers()
    }



    private fun setupObservers() {
        forecastVM.mutableScreenState.observe(viewLifecycleOwner, Observer {
            binding.listProgressBar.visibility = if (it == ScreenState.LOADING) VISIBLE else GONE
        })

        geolocationVM.currentFusedLocation.observe(viewLifecycleOwner, Observer { geolocation: Geolocation ->
            getForecasts(geolocation)
        })

        geolocationVM.geolocationPicked.observe(viewLifecycleOwner, Observer { geolocation: Geolocation? ->
            geolocation?.let { getForecasts(it) }
        })

        forecastVM.forecasts.observe(viewLifecycleOwner, Observer {
            Log.d("TEST", it.toString())
            forecastListAdapter.setSubmitList(it.forecasts)
        })
    }

    private fun getForecasts(geolocation: Geolocation) {
        forecastVM.getForecasts(geolocation)
    }


    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {}

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d("TEST", "onPermissionsGranted")
        updateCurrentFusedLocation()
    }

    private fun updateCurrentFusedLocation() {
        if (checkLocationPermission() && checkLocationProviderEnabled()) {
            geolocationVM.updateCurrentFusedLocation()
        }
    }
}
