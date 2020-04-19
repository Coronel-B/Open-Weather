package org.techdev.openweather.list.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.techdev.openweather.data.repository.ForecastResult

class ForecastListVM : ViewModel() {

    private val _progressVisiblity = MutableLiveData<Boolean>()
    val progressVisiblity: LiveData<Boolean> get() = _progressVisiblity

//    TODO:
    /*private val _forecastResult = MutableLiveData<ForecastResult>()
    val forecastResult: LiveData<ForecastResult> get() = _forecastResult*/
    private val _forecastResult = MutableLiveData<String>()
    val forecastResult: LiveData<String> get() = _forecastResult

    fun getForecast() {

//        Easy way to get off the main thread
        viewModelScope.launch {
            _progressVisiblity.value = true

//            The result is recovered in a 2nd plane so as not to block the main thread
            _forecastResult.value = withContext(Dispatchers.IO) {
//                TODO: ForecastResult()
                ""
            }

            _progressVisiblity.value = false
        }
    }


}