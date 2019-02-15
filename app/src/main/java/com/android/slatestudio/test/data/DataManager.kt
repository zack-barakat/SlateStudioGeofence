package com.android.slatestudio.test.data

import android.content.Context
import com.android.slatestudio.test.di.qualifiers.ApplicationContext
import com.android.slatestudio.test.di.scopes.ApplicationScope
import javax.inject.Inject

interface IDataManager {
    @ApplicationContext
    fun getApplicationContext(): Context

    fun getEventBusManager(): IEventBusManager

}

@ApplicationScope
class DataManager @Inject
constructor(@ApplicationContext val mApplicationContext: Context, val mEventBusManager: EventBusManager) :
    IDataManager {

    override fun getApplicationContext(): Context {
        return mApplicationContext
    }

    override fun getEventBusManager(): IEventBusManager {
        return mEventBusManager
    }
}
