package com.android.slatesutdio.test.shadows;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.android.slatesutdio.test.testUtils.TestSharedPreferences;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import java.util.HashMap;

@Implements(PreferenceManager.class)
public class ShadowPreferenceManager {
    private static SharedPreferences preferences = new TestSharedPreferences(new HashMap<>(), "__default__", Context.MODE_PRIVATE);

    @Implementation
    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        return preferences;
    }

    public static void reset() {
        preferences = new TestSharedPreferences(new HashMap<>(), "__default__", Context.MODE_PRIVATE);
    }
}
