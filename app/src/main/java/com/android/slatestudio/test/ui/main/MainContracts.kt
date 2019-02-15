package com.android.slatestudio.test.ui.main

import com.android.slatestudio.test.data.model.GeofenceModel
import com.android.slatestudio.test.ui.base.BasePresenter
import com.android.slatestudio.test.ui.base.BaseView

interface MainContracts {

    interface View : BaseView {

        fun showUpdatedStatus(newStatusText: String)

        fun openCreateGeofenceScreen()

        fun showUserCurrentLocation()

        fun showGeofence(geofenceModel: GeofenceModel)

        fun clearMapObjects()
    }

    interface Presenter<V : View> : BasePresenter<V> {

        fun onStart()

        fun onAddGeofenceClick()

        fun onMapReady()

        fun onDeleteGeofenceClicked(geofenceId: String)
    }
}
