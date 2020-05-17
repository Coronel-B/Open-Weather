package org.techdev.openweather.vm

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Source: https://stackoverflow.com/a/57610290/5279996
 */
class GeolocationVMTest {

    private lateinit var context: Context
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun fusedLocationClient_isNotNull() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        Assert.assertNotNull(fusedLocationClient)
    }

    /**
     * PRE: Entrar a un mapa y tocar el botón de Mi Ubicación
     */
    @Test
    fun location_isNotNull() {
        fusedLocationClient_isNotNull()

        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                Log.d("GeolocationVMTest", it.toString())
                Assert.assertNotNull(it)
            }

    }

}