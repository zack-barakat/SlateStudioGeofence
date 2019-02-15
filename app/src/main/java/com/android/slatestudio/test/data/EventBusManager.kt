package com.android.slatestudio.test.data

import android.content.Context
import com.android.slatestudio.test.di.qualifiers.ApplicationContext
import com.android.slatestudio.test.di.scopes.ApplicationScope
import javax.inject.Inject

interface IEventBusManager {

    fun getBus(): RxBus

    fun postEventSafely(event: Any)
}

@ApplicationScope
class EventBusManager @Inject
constructor(private val mBus: RxBus) : IEventBusManager {

    override fun getBus(): RxBus {
        return mBus
    }

    override fun postEventSafely(event: Any) {
        mBus.post(event)
    }
}
