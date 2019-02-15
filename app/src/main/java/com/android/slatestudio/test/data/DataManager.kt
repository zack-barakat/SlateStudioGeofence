package com.android.slatestudio.test.data

import android.content.Context
import com.android.slatestudio.test.data.repositories.GeofenceRepository
import com.android.slatestudio.test.data.repositories.IGeofenceRepository
import com.android.slatestudio.test.di.qualifiers.ApplicationContext
import com.android.slatestudio.test.di.scopes.ApplicationScope
import javax.inject.Inject

interface IDataManager {
    @ApplicationContext
    fun getApplicationContext(): Context

    fun getEventBusManager(): IEventBusManager

    fun getGeofenceRepository(): IGeofenceRepository
}

@ApplicationScope
class DataManager @Inject
constructor(
    @ApplicationContext val mApplicationContext: Context,
    private val mEventBusManager: EventBusManager,
    private val mGeofenceRepository: GeofenceRepository
) :
    IDataManager {

    override fun getApplicationContext(): Context {
        return mApplicationContext
    }

    override fun getEventBusManager(): IEventBusManager {
        return mEventBusManager
    }

    override fun getGeofenceRepository(): IGeofenceRepository {
        return mGeofenceRepository
    }
}
