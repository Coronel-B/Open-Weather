package org.techdev.openweather.list.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.techdev.openweather.data.repository.ForecastRepository

class ForecastListVM : ViewModel() {

    private val _progressVisiblity = MutableLiveData<Boolean>()
    val progressVisiblity: LiveData<Boolean> get() = _progressVisiblity

    private val forecastRepository = ForecastRepository()

//    TODO:
    /*private val _forecastResult = MutableLiveData<ForecastResult>()
    val forecastResult: LiveData<ForecastResult> get() = _forecastResult*/
    private val _forecastResult = MutableLiveData<JsonObject>()
    val forecastResult: LiveData<JsonObject> get() = _forecastResult

    fun getForecast() {

//        Easy way to get off the main thread
        viewModelScope.launch {
            _progressVisiblity.value = true

//            The result is recovered in a 2nd plane so as not to block the main thread
            _forecastResult.value = withContext(Dispatchers.IO) {
//                TODO: ForecastResult()
//                ""
                forecastRepository.getForecastString().value

            }

            _progressVisiblity.value = false
        }
    }


}