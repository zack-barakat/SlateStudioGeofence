package com.android.slatestudio.test.data.repositories

import android.content.Context
import com.android.slatestudio.test.data.model.GeofenceModel
import com.android.slatestudio.test.data.prefs.IPreferencesHelper
import com.android.slatestudio.test.di.qualifiers.ApplicationContext
import com.android.slatestudio.test.di.scopes.ApplicationScope
import javax.inject.Inject

interface IGeofenceRepository {
    fun getGeofences(): ArrayList<GeofenceModel>

    fun getGeofence(geofenceId: String): GeofenceModel?

    fun addGeofence(geofenceModel: GeofenceModel)

    fun removeGeofence(geofenceModel: GeofenceModel)
}

@ApplicationScope
open class GeofenceRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferencesHelper: IPreferencesHelper
) : IGeofenceRepository {

    override fun getGeofences(): ArrayList<GeofenceModel> {
        return preferencesHelper.getGeofences()
    }

    override fun getGeofence(geofenceId: String): GeofenceModel? {
        return preferencesHelper.getGeofence(geofenceId)
    }

    override fun addGeofence(geofenceModel: GeofenceModel) {
        preferencesHelper.addGeofence(geofenceModel)
    }

    override fun removeGeofence(geofenceModel: GeofenceModel) {
        preferencesHelper.removeGeofence(geofenceModel)
    }
}