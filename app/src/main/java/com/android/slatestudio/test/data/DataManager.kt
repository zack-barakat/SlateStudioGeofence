package com.android.slatestudio.test.data

import android.content.Context
import com.android.slatestudio.test.di.qualifiers.ApplicationContext
import com.android.slatestudio.test.di.scopes.ApplicationScope
import javax.inject.Inject

interface IDataManager {
    @ApplicationContext
    fun getApplicationContext(): Context
}

@ApplicationScope
class DataManager @Inject
constructor(@ApplicationContext val mApplicationContext: Context) : IDataManager {


    override fun getApplicationContext(): Context {
        return mApplicationContext
    }
}
