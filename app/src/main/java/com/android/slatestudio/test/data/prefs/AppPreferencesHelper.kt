package com.android.slatestudio.test.data.prefs

import android.content.Context
import android.content.SharedPreferences
import com.android.slatestudio.test.data.model.GeofenceModel
import com.android.slatestudio.test.di.qualifiers.ApplicationContext
import com.android.slatestudio.test.di.scopes.ApplicationScope
import com.google.gson.Gson
import javax.inject.Inject

interface IPreferencesHelper {

    fun getGeofences(): ArrayList<GeofenceModel>

    fun getGeofence(geofenceId: String): GeofenceModel?

    fun addGeofence(geofenceModel: GeofenceModel)

    fun saveGeofences(geofences: ArrayList<GeofenceModel>)

    fun removeGeofence(geofenceModel: GeofenceModel)
}

@ApplicationScope
class AppPreferencesHelper @Inject
constructor(@ApplicationContext context: Context) : IPreferencesHelper {

    companion object {
        private const val PREF_FILE_NAME = "SlateStudio"
        private const val KEY_GEOFENCES = "GEOFENCES"
    }

    private val mSharedPreferences: SharedPreferences
    private val mPreferenceEditors
        get() = mSharedPreferences.edit()

    private val gson = Gson()

    init {
        mSharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }

    override fun getGeofences(): ArrayList<GeofenceModel> {
        if (mSharedPreferences.contains(KEY_GEOFENCES)) {
            val geofencesString = mSharedPreferences.getString(KEY_GEOFENCES, null)
            val arrayOfGeofences = gson.fromJson(
                geofencesString,
                Array<GeofenceModel>::class.java
            )

            if (arrayOfGeofences != null) {
                return ArrayList(arrayOfGeofences.toList())
            }
        }
        return arrayListOf()
    }

    override fun getGeofence(geofenceId: String): GeofenceModel? {
        val geofences = getGeofences()
        return geofences.firstOrNull { it.id == geofenceId }
    }

    override fun addGeofence(geofenceModel: GeofenceModel) {
        val geofences = getGeofences()
        val newGeofences = ArrayList(geofences + geofenceModel)
        saveGeofences(newGeofences)
    }

    override fun saveGeofences(geofences: ArrayList<GeofenceModel>) {
        mPreferenceEditors
            .putString(KEY_GEOFENCES, gson.toJson(geofences.toArray()))
            .apply()
    }

    override fun removeGeofence(geofenceModel: GeofenceModel) {
        val geofences = getGeofences()
        val newGeofences = ArrayList(geofences - geofenceModel)
        saveGeofences(newGeofences)
    }
}
