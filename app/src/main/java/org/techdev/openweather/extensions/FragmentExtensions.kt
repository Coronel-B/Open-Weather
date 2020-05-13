package org.techdev.openweather.extensions

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.techdev.openweather.BuildConfig
import org.techdev.openweather.R
import pub.devrel.easypermissions.EasyPermissions

/**
 * PRO: Describe la url de un ícono
 * @param: 10n, 13d
 * Source: https://openweathermap.org/weather-conditions
 */
fun Fragment.getIconUrl(icon: String): String {
    val baseImageUrl = BuildConfig.BASE_IMG_URL
    val extension = "@2x.png"
    val pathAppId = "?appid=${BuildConfig.APP_ID}"
    return baseImageUrl + icon + extension + pathAppId
}

/**
 * PRO: Realiza la solicitud del permiso de ubicación
 * PRE: El permiso no tiene que estar habilitado
 * Fuente: https://stackoverflow.com/a/54971368/5279996
 */
fun Fragment.requestLocationPermission() {
    val perm1 = Manifest.permission.ACCESS_FINE_LOCATION
//        val perm2 = ""
    EasyPermissions.requestPermissions(
        this, "Please grant the location permission",
        LOCATION_PERMISSION_REQUEST_CODE,
        perm1
//            , perm2
    )
}

/**
 * PRO: Describe si el permiso de ubicación está habilitado
 */
fun Fragment.checkLocationPermission(): Boolean {
    val perms = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    return EasyPermissions.hasPermissions(context!!, *perms)
}

/**
 * PRO: Describe si un proveedor de ubicación está habilitado
 * OBS: El provedor puede ser: network or gps
 * @return
 * Fuente: https://stackoverflow.com/a/10311891/5279996
 */
fun Fragment.checkLocationProviderEnabled(): Boolean {
    val locationManager =
        context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    var locationProviderEnabled = false
    try {
        locationProviderEnabled =
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    } catch (ignored: Exception) {
    }
    return locationProviderEnabled
}

/**
 * PRO: Realiza la petición para que el usuario pueda habilitar la ubicación
 * PRE: La ubicación tiene que estar desactivada
 */
fun Fragment.requestEnableLocationProvider() {
    AlertDialog.Builder(context)
        .setMessage(R.string.gps_not_enabled)
        .setPositiveButton(
            R.string.open_location_settings
        ) { dialogInterface, i ->
            context!!.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
        .setNegativeButton(R.string.cancel, null)
        .show()
}

const val LOCATION_PERMISSION_REQUEST_CODE = 1
