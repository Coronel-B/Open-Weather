package org.techdev.openweather.map.domain

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Geolocation(
    val geolocation: LatLng
) : Parcelable