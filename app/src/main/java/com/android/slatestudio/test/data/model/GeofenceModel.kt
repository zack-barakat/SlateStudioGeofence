package com.android.slatestudio.test.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class GeofenceModel(
    val id: String = UUID.randomUUID().toString(),
    var lat: Double,
    var lng: Double,
    var radius: Double
) : Parcelable