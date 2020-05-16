package org.techdev.openweather.map.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog.Builder
import android.content.Context
import android.content.Intent
import android.location.GnssStatus.Callback
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.*
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import org.techdev.openweather.R
import org.techdev.openweather.map.domain.Geolocation
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks

/**
 * OBS:
 * LocationListener se implementa para conocer el estado del GPS
 */
class LocationPickerMapFragment : SupportMapFragment(),
    OnMapReadyCallback,
    OnMapClickListener,
    OnMyLocationClickListener,
    OnMyLocationButtonClickListener,
    LocationListener,
    PermissionCallbacks {

    val TAG = this::class.java.simpleName

    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    private lateinit var googleMap: GoogleMap
    private var gnssStatusCallback: Callback? = null
    private var locationManager: LocationManager? = null

    companion object {
        fun newInstance() =
            LocationPickerMapFragment()
    }

    /**
     * PRO: Escucha del cambio de estado del GPS
     * OBS: Se usó la interfaz GnssStatus.Callback
     * Fuente:
     * . https://stackoverflow.com/a/43818318/5279996
     * . https://developer.android.com/reference/android/location/GnssStatus.Callback.html
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!checkLocationPermission()) requestLocationPermission()
        if (!checkLocationProviderEnabled()) {
            requestEnableLocationProvider()
        }
        locationManager = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            gnssStatusCallback = object : Callback() {
                override fun onStarted() {
                    super.onStarted()
                    if (checkLocationPermission() && checkLocationProviderEnabled()) {
                        setMyLocationEnabled(true)
                    }
                }

                override fun onStopped() {
                    super.onStopped()
                    if (!checkLocationPermission() || !checkLocationProviderEnabled()) {
                        setMyLocationEnabled(false)
                    }
                }
            }
        }
    }

    /**
     * PRO: Habilita la escucha del GPS si el permiso está activado
     * OBS: Esta lógica no funciona en onStart
     * Fuente: https://stackoverflow.com/a/40142454/5279996
     */
    override fun onResume() {
        super.onResume()
        if (checkLocationPermission() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locationManager!!.registerGnssStatusCallback(gnssStatusCallback)
            locationManager!!.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 400, 0f, this
            )
        }
    }

    /**
     * PRO: Desactiva la escucha del GPS
     */
    override fun onStop() {
        locationManager!!.removeUpdates(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locationManager!!.unregisterGnssStatusCallback(gnssStatusCallback)
        }
        super.onStop()
    }

    override fun onActivityCreated(bundle: Bundle?) {
        super.onActivityCreated(bundle)
        //        Método de asociación para registrar la actividad como escucha (onMapReadyCallback)
        getMapAsync(this)
    }

    /**
     * PRO: Establece o no el botón y el marcador de mi ubicación
     * PRE: Call requires permission which may be rejected by user
     * OBS: La ventaja de este método es que lo puedo usar dentro de callbacks
     */
    @SuppressLint("MissingPermission")
    private fun setMyLocationEnabled(set: Boolean) {
        if (::googleMap.isInitialized)
            googleMap.isMyLocationEnabled = set
    }

    /**
     * La clase GoogleMap es la médula de toda la API, ya que representa al mapa y permite manejar los gráficos
     * sobre este, transformar la cámara, escuchar eventos, tomar instantáneas, etc...
     * PRE: Tiene que existir una asociación para que este método sea llamado.
     * Para visualizar el botón de mi ubicación asegurarme de que la Toolbar no lo tape con CoordinatorLayout.
     * OBS: En el emulador mi ubicación y el botón no aparecen se activan luego de la primera confirmación del permiso
     * Fuente:
     * . My Answer: https://stackoverflow.com/a/54971368/5279996
     */
    override fun onMapReady(map: GoogleMap?) { //        Guardo la instancia GoogleMap obtenida en este método en una campo global, que brinde futuros accesos.
        if (map != null) {
            this.googleMap = map
            this.googleMap = animateCameraBsAs(map)
            //        Relaciono el mapa con la escucha
            this.googleMap.setOnMapClickListener(this)
            //        Si se concedió el permiso y el gps está activado, se establece el marcador y botón de mi ubicación
            if (checkLocationPermission() && checkLocationProviderEnabled()) {
                setMyLocationEnabled(true)
            } else {
                setMyLocationEnabled(false)
            }
            this.googleMap.setOnMyLocationButtonClickListener(this)
            this.googleMap.setOnMyLocationClickListener(this)
        }
    }

    /**
     * PRO: Anima la cámara hacia Buenos Aires
     */
    private fun animateCameraBsAs(googleMap: GoogleMap): GoogleMap {
        val buenosAires = LatLng(-34.607562, -58.437076)
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(buenosAires, 10f),
            2000,
            object : CancelableCallback {
                override fun onFinish() {
                    Log.d("TEST", "Buenos Aires")
                }

                override fun onCancel() {}
            }
        )
        return googleMap
    }

    /**
     * PRO: Describe si el permiso de ubicación está habilitado
     */
    private fun checkLocationPermission(): Boolean {
        val perms = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        return EasyPermissions.hasPermissions(context!!, *perms)
    }

    /**
     * PRO: Realiza la solicitud del permiso de ubicación
     * PRE: El permiso no tiene que estar habilitado
     * Fuente: https://stackoverflow.com/a/54971368/5279996
     */
    private fun requestLocationPermission() {
        val perm1 = Manifest.permission.ACCESS_FINE_LOCATION
//        val perm2 = ""
        EasyPermissions.requestPermissions(
            this, "Please grant the location permission",
            LOCATION_PERMISSION_REQUEST_CODE,
            perm1
//            , perm2
        )
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String?>) {
        if (checkLocationPermission() && checkLocationProviderEnabled()) {
            setMyLocationEnabled(true)
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String?>) {
        if (!checkLocationPermission() || !checkLocationProviderEnabled()) {
            setMyLocationEnabled(false)
        }
    }

    /**
     * PRO: Describe si un proveedor de ubicación está habilitado
     * OBS: El provedor puede ser: network or gps
     * @return
     * Fuente: https://stackoverflow.com/a/10311891/5279996
     */
    private fun checkLocationProviderEnabled(): Boolean {
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
    private fun requestEnableLocationProvider() {
        Builder(context)
            .setMessage(R.string.gps_not_enabled)
            .setPositiveButton(R.string.open_location_settings
            ) { dialogInterface, i ->
                context!!.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    /**
     * PRO: Proceso el evento de la screen
     * PRE:
     * . Implementar la interfaz OnMapClickListener
     * . Asignar la escucha al mapa con GoogleMap.setOnMapClickListener()
     * OBS:
     * Se usa la interfaz OnMapClickListener.
     */
    override fun onMapClick(latLng: LatLng) {
        val activity = activity
        //        Comparo que la acción del intent entrante del contexto sea de selección
        if (LocationMapsActivity.ACTION_PICK_LOCATION == activity!!.intent.action
        ) { //            Llamada al Geocoder en la UI del mapa
            sendGeolocationToHomeSecren(
                latLng.latitude,
                latLng.longitude
            )
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        return false
    }

    override fun onMyLocationClick(location: Location) {
        val activity = activity
        //        Comparo que la acción del intent entrante del contexto sea de selección
        if (LocationMapsActivity.ACTION_PICK_LOCATION
                .equals(activity!!.intent.action)
        ) { //            Llamada al Geocoder en la UI del mapa
            sendGeolocationToHomeSecren(
                location.latitude,
                location.longitude
            )
        }
    }

    /**
     * PRO: Simplifica la creación de un intent de respuesta con el extra de la geolocalización
     *
     */
    private fun showHomeScreen(selectedLocation: Geolocation) {
        val responseIntent = Intent()
        responseIntent.putExtra(LocationMapsActivity.EXTRA_LOCATION, selectedLocation)
        activity!!.setResult(Activity.RESULT_OK, responseIntent)
        activity!!.finish()
    }

    /**
     * PRO: Geocoder call. Obtiene la respuesta del Geocoder sin bloquear la UI.
     * OBS: Este procedimiento puede estar ubicado en una clase auxiliar.
     * Se usa un thread y un hanlder p/dicho propósito.
     */
    private fun sendGeolocationToHomeSecren(latitude: Double, longitude: Double) {
        val geolocation = Geolocation(LatLng(latitude, longitude))
        showHomeScreen(geolocation)
    }


    override fun onLocationChanged(location: Location?) {}

    override fun onStatusChanged(s: String?, i: Int, bundle: Bundle?) {}

    override fun onProviderEnabled(s: String?) {}

    override fun onProviderDisabled(s: String?) {}

}
