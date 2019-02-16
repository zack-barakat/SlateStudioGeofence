package com.android.slatestudio.test.ui.creategeofence

import android.support.annotation.VisibleForTesting
import com.android.slatestudio.test.R
import com.android.slatestudio.test.data.IDataManager
import com.android.slatestudio.test.data.model.GeofenceModel
import com.android.slatestudio.test.ui.base.BaseMvpPresenter
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class CreateGeofencePresenter @Inject
constructor(dataManager: IDataManager) : BaseMvpPresenter<CreateGeofenceContracts.View>(dataManager),
    CreateGeofenceContracts.Presenter<CreateGeofenceContracts.View> {

    @VisibleForTesting
    var mGeofenceModel = GeofenceModel(lat = 0.0, lng = 0.0, radius = 2000.0)
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
        mGeofenceRepository.addGeofence(mGeofenceModel)
        view.showGeofenceAddedSuccessfully()
    }
}
