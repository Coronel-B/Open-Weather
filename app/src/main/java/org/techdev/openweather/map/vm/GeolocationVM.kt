package org.techdev.openweather.map.vm

import android.Manifest
import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import org.techdev.openweather.util.OWViewModel
import pub.devrel.easypermissions.EasyPermissions

/**
 * PRE: El permiso de ubicación está habilitado
 */
class GeolocationVM(val context: Context) : OWViewModel() {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    private val _currentLatLngLocation = MutableLiveData<LatLng>()

    val currentLatLngLocation: LiveData<LatLng> = _currentLatLngLocation

    init {
        Log.d("TEST", "@Singleton LocationVM")
        getCurrentLocation()
    }

    /**
     * PRE: El permiso de ubicación está habilitado
     */
    private fun getCurrentLocation() {
        Log.d("TEST", "Geolocation before")
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                _currentLatLngLocation.value = LatLng(it.latitude, it.longitude)
                Log.d("TEST", LatLng(it.latitude, it.longitude).toString())
            }
    }

    /**
     * PRO: Actualiza la ubicacion actual
     * PRE: El permiso de ubicación está habilitado
     * OBS: Caso de uso: Cada vez que se habilite el permiso de ubicación
     */
    fun updateCurrentLocation() {
        getCurrentLocation()
    }

    fun setCurrentLocation(newLatLngLocation: LatLng) {
        _currentLatLngLocation.value = newLatLngLocation
    }

}