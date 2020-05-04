package org.techdev.openweather.util

import org.techdev.openweather.util.ErrorType

/**
 * PRO: Notify the errors to the classes
 */
interface RemoteErrorEmitter {
    fun onError(msg: String)
    fun onError(errorType: ErrorType)
}