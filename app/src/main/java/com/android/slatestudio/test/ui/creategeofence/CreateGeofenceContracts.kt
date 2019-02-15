package com.android.slatestudio.test.ui.creategeofence

import com.google.android.gms.maps.model.LatLng
import com.android.slatestudio.test.data.model.GeofenceModel
import com.android.slatestudio.test.ui.base.BasePresenter
import com.android.slatestudio.test.ui.base.BaseView

interface CreateGeofenceContracts {

    interface View : BaseView {

        fun showSelectLocationView()

        fun showSelectRadiusView()

        fun showGeofenceAddedSuccessfully()

        fun centerMap(extraLatLng: LatLng, extraZoom: Float)

        fun showUpdatedGeofence(geofenceModel: GeofenceModel, radiusLabel: String)
    }

    interface Presenter<V : View> : BasePresenter<V> {

        fun initPresenter(latLng: LatLng, zoom: Float)

        fun onMapReady()

        fun onSelectLocation(latLng: LatLng)

        fun onUpdateRadius(radius: Int)

        fun onCreateGeofenceClick()
    }
}
