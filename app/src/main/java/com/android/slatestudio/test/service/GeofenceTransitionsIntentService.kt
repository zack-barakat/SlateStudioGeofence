package com.android.slatestudio.test.service

import android.app.IntentService
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import com.android.slatestudio.test.App
import com.android.slatestudio.test.R
import com.android.slatestudio.test.data.IEventBusManager
import com.android.slatestudio.test.data.busevent.GeofenceTransitionEvent
import com.android.slatestudio.test.di.component.DaggerServiceComponent
import com.android.slatestudio.test.di.component.ServiceComponent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import javax.inject.Inject

class GeofenceTransitionsIntentService : IntentService("GeofenceTransitionsIntentService") {

    private var mServiceComponent: ServiceComponent? = null

    @Inject
    lateinit var mEventBusManager: IEventBusManager

    override fun onCreate() {
        getServiceComponent().inject(this)
        super.onCreate()
    }

    private fun createComponent() {
        mServiceComponent = DaggerServiceComponent.builder()
            .applicationComponent((application as App).component)
            .build()
    }

    fun getServiceComponent(): ServiceComponent {
        if (mServiceComponent == null) {
            createComponent()
        }
        return mServiceComponent!!
    }

    override fun onHandleIntent(intent: Intent?) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            val errorMessage = getGeofenceErrorString(geofencingEvent.errorCode)
            Log.e(TAG, errorMessage)
            return
        }

        handleGeofenceEvent(geofencingEvent)
    }

    private fun handleGeofenceEvent(geofencingEvent: GeofencingEvent) {
        val geofenceTransition = geofencingEvent.geofenceTransition
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER || geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            val geofenceTransitionMessage = getGeofenceTransitionMessage(geofenceTransition)
            // emit event bus event
            mEventBusManager.postEventSafely(GeofenceTransitionEvent(geofenceTransitionMessage))
        } else {
            // Log the error.
            Log.e(TAG, getString(R.string.geofence_transition_invalid_type))
        }
    }

    private fun getGeofenceTransitionMessage(geofenceTransition: Int): String {
        return if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            getString(R.string.text_inside)
        } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            getString(R.string.text_outside)
        } else {
            getString(R.string.geofence_transition_invalid_type)
        }
    }

    private fun getGeofenceErrorString(errorCode: Int): String {
        return when (errorCode) {
            GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE ->
                resources.getString(R.string.geofence_not_available)

            GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES ->
                resources.getString(R.string.geofence_too_many_geofences)

            GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS ->
                resources.getString(R.string.geofence_too_many_pending_intents)

            else -> resources.getString(R.string.geofence_unknown_error)
        }
    }
}