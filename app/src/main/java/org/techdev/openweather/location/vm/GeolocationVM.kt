package org.techdev.openweather.location.vm

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import org.techdev.openweather.location.domain.Geolocation
import org.techdev.openweather.util.OWViewModel

/**
 * PRE: El permiso de ubicación está habilitado
 */
class GeolocationVM(val context: Context) : OWViewModel() {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    private val _currentFusedLocation = MutableLiveData<Geolocation>()
    val currentFusedLocation: LiveData<Geolocation> = _currentFusedLocation

    private val _geolocationPicked = MutableLiveData<Geolocation?>()
    val geolocationPicked: LiveData<Geolocation?> = _geolocationPicked

    init {
        Log.d("GeolocationVM", "@Singleton LocationVM")
        getCurrentFusedLocation()
    }

    /**
     * PRE: El permiso de ubicación está habilitado
     */
    private fun getCurrentFusedLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    setCurrentFusedLocation(Geolocation(LatLng(location.latitude, location.longitude)))

//                    Ubicación definida cuando se denega el permiso
                } else {
                    setCurrentFusedLocation(Geolocation(LatLng(-30.0, -60.0)))
                }
            }
    }

    /**
     * PRO: Actualiza la ubicacion actual
     * PRE: El permiso de ubicación está habilitado
     * OBS: Caso de uso: Cada vez que se habilite el permiso de ubicación
     */
    fun updateCurrentFusedLocation() {
        getCurrentFusedLocation()
    }

    fun setCurrentFusedLocation(newLatLngLocation: Geolocation) {
        _currentFusedLocation.value = newLatLngLocation
    }

    fun setGeolocationPicked(geolocation: Geolocation?) {
        _geolocationPicked.value = geolocation
    }

}