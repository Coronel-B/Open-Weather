package org.techdev.openweather.location.vm

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import org.techdev.openweather.MainActivity
import org.techdev.openweather.location.domain.LastLocation
import org.techdev.openweather.util.OWViewModel

/**
 * Source: https://stackoverflow.com/a/18463758/5279996
 */
class ChangeLocationDialogVM: OWViewModel() {

    private var sharedPreferences: SharedPreferences? = null

    fun provideSharedPreferences(sharedPreferences: SharedPreferences?) {
        this.sharedPreferences = sharedPreferences
    }

    /**
     * PRE: SharedPreference esta inicializado
     */
    fun getLastLocations(): ArrayList<LastLocation> {
        return if (sharedPreferences != null) {
            val gson = Gson()
            val json: String? = sharedPreferences!!.getString("lastLocations", "")
            gson.fromJson(json, ArrayList<LastLocation>()::class.java)
        } else {
            arrayListOf()
        }
    }

    fun addLastLocationToList(lastLocation: LastLocation) {
        if (sharedPreferences != null) {
            if (thereIsSlot()) {
                add(lastLocation)
            } else {
                remove(lastLocation)
                add(lastLocation)
            }
        }
    }

    private fun thereIsSlot(): Boolean {
        return getLastLocations().isNullOrEmpty() ||
                getLastLocations().size <= 5
    }

    private fun add(lastLocation: LastLocation) {
        val gson = Gson()

        val lastLocations = getLastLocations()
        lastLocations.add(lastLocation)

        val json: String = gson.toJson(lastLocations)

        with(sharedPreferences!!.edit()) {
            putString("lastLocations", json)
            commit()
        }
    }

    private fun remove(lastLocation: LastLocation) {
        val lastLocations = getLastLocations()
        lastLocations.remove(lastLocation)
    }






}