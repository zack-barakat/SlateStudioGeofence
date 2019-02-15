package com.android.slatestudio.test.data.prefs

import android.content.Context
import android.content.SharedPreferences
import com.android.slatestudio.test.di.qualifiers.ApplicationContext
import com.android.slatestudio.test.di.scopes.ApplicationScope
import javax.inject.Inject

interface IPreferencesHelper {
}

@ApplicationScope
class AppPreferencesHelper @Inject
constructor(@ApplicationContext context: Context) : IPreferencesHelper {

    companion object {
        private const val PREF_FILE_NAME = "SlateStudio"
        private const val KEY_GEOFENCES = "GEOFENCES"
    }

    private val mSharedPreferences: SharedPreferences
    private val mPreferenceEditors
        get() = mSharedPreferences.edit()

    init {
        mSharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }
}
