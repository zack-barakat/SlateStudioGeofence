package com.android.slatestudio.test.data.repositories

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import com.android.slatestudio.test.data.model.GeofenceModel
import com.android.slatestudio.test.data.prefs.IPreferencesHelper
import com.android.slatestudio.test.di.qualifiers.ApplicationContext
import com.android.slatestudio.test.di.scopes.ApplicationScope
import com.android.slatestudio.test.service.GeofenceTransitionsIntentService
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import javax.inject.Inject

interface IGeofenceRepository {
    fun getGeofences(): ArrayList<GeofenceModel>

    fun getGeofence(geofenceId: String): GeofenceModel?

    fun addGeofence(geofenceModel: GeofenceModel)

    fun removeGeofence(geofenceModel: GeofenceModel)

    fun trackGeofenceTransition(geofenceModel: GeofenceModel)
}

@ApplicationScope
open class GeofenceRepository @Inject constructor(
    @ApplicationContext private val mAppContext: Context,
    private val preferencesHelper: IPreferencesHelper
) : IGeofenceRepository {


    private val mGeofencingClient = LocationServices.getGeofencingClient(mAppContext)

    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(mAppContext, GeofenceTransitionsIntentService::class.java)
        PendingIntent.getService(mAppContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun getGeofences(): ArrayList<GeofenceModel> {
        return preferencesHelper.getGeofences()
    }

    override fun getGeofence(geofenceId: String): GeofenceModel? {
        return preferencesHelper.getGeofence(geofenceId)
    }

    override fun addGeofence(geofenceModel: GeofenceModel) {
        preferencesHelper.addGeofence(geofenceModel)
        trackGeofenceTransition(geofenceModel)
    }

    override fun removeGeofence(geofenceModel: GeofenceModel) {
        preferencesHelper.removeGeofence(geofenceModel)

        // clear it from geofencing and track the rest of geofences
        mGeofencingClient.removeGeofences(geofencePendingIntent)
        getGeofences().forEach {
            trackGeofenceTransition(it)
        }
    }

    override fun trackGeofenceTransition(geofenceModel: GeofenceModel) {
        val geofence = buildGeofence(geofenceModel)
        if (ContextCompat.checkSelfPermission(
                mAppContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mGeofencingClient
                .addGeofences(buildGeofencingRequest(geofence), geofencePendingIntent)
                .addOnSuccessListener {
                }
                .addOnFailureListener {
                }
        }
    }

    private fun buildGeofence(geofenceModel: GeofenceModel): Geofence {
        val latitude = geofenceModel.lat
        val longitude = geofenceModel.lng
        val radius = geofenceModel.radius

        return Geofence.Builder()
            .setRequestId(geofenceModel.id)
            .setCircularRegion(
                latitude,
                longitude,
                radius.toFloat()
            )
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .build()
    }

    private fun buildGeofencingRequest(geofence: Geofence): GeofencingRequest {
        return GeofencingRequest.Builder()
            .setInitialTrigger(0)
            .addGeofences(listOf(geofence))
            .build()
    }
}