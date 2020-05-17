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
import androidx.recyclerview.widget.LinearLayoutManager

import org.techdev.openweather.databinding.FragmentListForecastBinding
import org.techdev.openweather.forecast.vm.ForecastListVM
import org.techdev.openweather.map.vm.GeolocationVM
import org.techdev.openweather.util.ScreenState
import pub.devrel.easypermissions.EasyPermissions

/**
 * PRO: Render a list of daily forecasts for the next 5 days
 */
class ForecastListFragment(private val geolocationVM: GeolocationVM) : Fragment(),
    EasyPermissions.PermissionCallbacks {

    private lateinit var binding: FragmentListForecastBinding

    private lateinit var forecastVM: ForecastListVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListForecastBinding.inflate(layoutInflater)

        forecastVM = ViewModelProvider(this).get()

        binding.listRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
/*
        setupObservers()
        getForecasts()*/
    }



    private fun setupObservers() {
        forecastVM.mutableScreenState.observe(viewLifecycleOwner, Observer {
            binding.listProgressBar.visibility = if (it == ScreenState.LOADING) VISIBLE else GONE
        })

        forecastVM.forecasts.observe(viewLifecycleOwner, Observer {
            Log.d("TEST", it.toString())

            //            TODO: Set Adapter
//            binding.listRecyclerView.adapter = ForecastListAdapter()
        })
    }

    private fun getForecasts() {
        forecastVM.getForecasts()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
