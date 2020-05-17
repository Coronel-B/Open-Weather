package org.techdev.openweather.repository

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.techdev.openweather.current.data.repository.WeatherResponse
import org.techdev.openweather.current.data.service.WeatherService
import org.techdev.openweather.data.retrofit.RetrofitService
import org.techdev.openweather.data.retrofit.service.APICallManager
import org.techdev.openweather.map.domain.Geolocation
import org.techdev.openweather.util.ErrorType
import org.techdev.openweather.util.RemoteErrorEmitter

/**
 * Source:
 *  . https://github.com/Kotlin/kotlinx.coroutines/tree/master/kotlinx-coroutines-test
 *  . https://craigrussell.io/2019/11/unit-testing-coroutine-suspend-functions-using-testcoroutinedispatcher/
 */
class WeatherRemoteRepositoryTest : RemoteErrorEmitter {

    private val mainThreadSurrogate = newSingleThreadContext("UI Thread")

    private lateinit var context: Context
    private lateinit var weatherService: WeatherService
    private lateinit var apiCallManager: APICallManager
    private lateinit var geolocation: Geolocation

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)

        context = ApplicationProvider.getApplicationContext()
        weatherService = RetrofitService.createService(WeatherService::class.java)
        apiCallManager = APICallManager()
        geolocation = Geolocation(LatLng(-30.0, -60.0))
    }

    @After
    fun tearDown() {
//        Reset main dispatcher to the original Main dispatcher
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    /**
     * OBS: Usar runBlocking pues runBlockingTest produce un bug
     * Source: https://github.com/Kotlin/kotlinx.coroutines/issues/1204
     */
    @Test
    fun weatherResponse() = runBlocking {
        weatherResponse_isNotNull()
    }


    private suspend fun weatherResponse_isNotNull() {
        coroutineScope {
            launch {
                Assert.assertNotNull(getWeather())
            }
        }
    }

    private suspend fun getWeather(): WeatherResponse? {
        return apiCallManager.executeSafeApiCall(this@WeatherRemoteRepositoryTest) {
            weatherService.getWeather(
                latitude = geolocation.geolocation.latitude.toString(),
                longitude = geolocation.geolocation.longitude.toString()
            )
        }
    }

    override fun onError(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(errorType: ErrorType) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}