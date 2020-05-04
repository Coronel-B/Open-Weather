package org.techdev.openweather.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class OWViewModel : ViewModel(),
    RemoteErrorEmitter {

    val mutableScreenState = MutableLiveData<ScreenState>().apply { value = ScreenState.RENDER }
    val mutableErrorMessage = MutableLiveData<String>()
    val mutableErrorType = MutableLiveData<ErrorType>()

    override fun onError(msg: String) {
        mutableErrorMessage.postValue(msg)
    }

    override fun onError(errorType: ErrorType) {
        mutableErrorType.postValue(errorType)
    }
}