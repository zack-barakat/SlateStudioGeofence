package com.android.slatestudio.test.ui.creategeofence

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import com.android.slatestudio.test.R
import com.android.slatestudio.test.data.IDataManager
import com.android.slatestudio.test.data.model.GeofenceModel
import com.android.slatestudio.test.service.GeofenceTransitionsIntentService
import com.android.slatestudio.test.ui.base.BaseMvpPresenter
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class CreateGeofencePresenter @Inject
constructor(dataManager: IDataManager) : BaseMvpPresenter<CreateGeofenceContracts.View>(dataManager),
    CreateGeofenceContracts.Presenter<CreateGeofenceContracts.View> {


    private val mGeofencingClient = LocationServices.getGeofencingClient(mAppContext)

    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(mAppContext, GeofenceTransitionsIntentService::class.java)
        PendingIntent.getService(mAppContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private var mGeofenceModel = GeofenceModel(lat = 0.0, lng = 0.0, radius = 2000.0)
    private lateinit var mExtraLatLng: LatLng
    private var mExtraZoom: Float = 0F

    override fun onAttachView(view: CreateGeofenceContracts.View) {
        super.onAttachView(view)
        view.showSelectLocationView()
    }


    override fun initPresenter(latLng: LatLng, zoom: Float) {
        mExtraLatLng = latLng
        mExtraZoom = zoom
    }

    override fun onMapReady() {
        view.centerMap(mExtraLatLng, mExtraZoom)
    }

    override fun onSelectLocation(latLng: LatLng) {
        mGeofenceModel.lat = latLng.latitude
        mGeofenceModel.lng = latLng.longitude
        view.showSelectRadiusView()
        updateGeofenceView()
    }

    override fun onUpdateRadius(radius: Int) {
        mGeofenceModel.radius = ((radius + 1) * 1000).toDouble()
        updateGeofenceView()
    }

    private fun updateGeofenceView() {
        view.showUpdatedGeofence(
            mGeofenceModel,
            String.format(mAppContext.getString(R.string.text_radius_size), (mGeofenceModel.radius / 1000).toInt())
        )
    }

    override fun onCreateGeofenceClick() {
        val geofence = buildGeofence(mGeofenceModel)
        if (ContextCompat.checkSelfPermission(
                mAppContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mGeofencingClient
                .addGeofences(buildGeofencingRequest(geofence), geofencePendingIntent)
                .addOnSuccessListener {
                    mGeofenceRepository.addGeofence(mGeofenceModel)
                    view.showGeofenceAddedSuccessfully()
                }
                .addOnFailureListener {
                }

        }
        view.showGeofenceAddedSuccessfully()
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
