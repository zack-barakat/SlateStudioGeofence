package com.android.slatestudio.test.ui.main

import com.android.slatestudio.test.data.IDataManager
import com.android.slatestudio.test.data.busevent.GeofenceTransitionEvent
import com.android.slatestudio.test.ui.base.BaseMvpPresenter
import javax.inject.Inject

class MainPresenter @Inject
constructor(dataManager: IDataManager) : BaseMvpPresenter<MainContracts.View>(dataManager),
    MainContracts.Presenter<MainContracts.View> {

    override fun onAttachView(view: MainContracts.View?) {
        super.onAttachView(view)
        subscribeToEventBus(GeofenceTransitionEvent::class.java) { event ->
            if (event.isSuccess) {
                view?.showUpdatedStatus(event.transitionType)
            }
        }
    }

    override fun onStart() {
        mGeofenceRepository.getGeofences().forEach { geofence ->
            view.showGeofence(geofence)
        }
    }
    override fun onAddGeofenceClick() {
        view.openCreateGeofenceScreen()
    }

    override fun onMapReady() {
        view.showUserCurrentLocation()
    }
}
