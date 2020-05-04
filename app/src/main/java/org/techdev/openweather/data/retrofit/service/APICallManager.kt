package org.techdev.openweather.data.retrofit.service

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import org.techdev.openweather.util.ErrorType
import org.techdev.openweather.util.RemoteErrorEmitter
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import java.net.SocketTimeoutException

class APICallManager {

    /**
     * PRO: Execute a safe api call and handle exception on http calls
     * OBS:
     *  . Higher order functions is used
     *  . context to Dispatchers.IO
     */
    suspend inline fun <C> executeSafeApiCall(emitter: RemoteErrorEmitter, crossinline responseFunction: suspend () -> C) : C? {
        return try {
//            The response is recovered in a 2nd plane so as not to block the main thread
            val response = withContext(Dispatchers.IO) {
                responseFunction.invoke()
            }
            response
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("ServiceManager", "Call error: ${e.localizedMessage}", e.cause)
            when (e) {
                is HttpException -> {
                    val body = e.response()?.errorBody()
                    emitter.onError(getErrorMessage(body))
                }
                is SocketTimeoutException -> emitter.onError(ErrorType.TIMEOUT)
                is IOException -> emitter.onError(ErrorType.NETWORK)
                else -> emitter.onError(ErrorType.UNKNOWN)
            }
            null
        }
    }


    /**
     * PRO: Gets the error message from the API checking what kind of JSON object use the API
     * for error response and the error codes.
     */
    fun getErrorMessage(responseBody: ResponseBody?): String {
        return try {
            val jsonObject = JSONObject(responseBody!!.string())
            when {
                jsonObject.has(MESSAGE_KEY) -> jsonObject.getString(MESSAGE_KEY)
                jsonObject.has(ERROR_KEY) -> jsonObject.getString(ERROR_KEY)
                else -> "Something wrong happened"
            }
        } catch (e: Exception) {
            "Something wrong happened"
        }
    }

    companion object {
        private const val MESSAGE_KEY = "message"
        private const val ERROR_KEY = "error"
    }

}