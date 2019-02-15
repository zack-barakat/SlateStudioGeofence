package com.android.slatestudio.test.di.module;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.android.slatestudio.test.data.prefs.AppPreferencesHelper;
import com.android.slatestudio.test.data.prefs.IPreferencesHelper;
import com.android.slatestudio.test.di.qualifiers.ApplicationContext;
import com.android.slatestudio.test.di.scopes.ApplicationScope;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by zack_barakat
 */

@Module
public abstract class ApplicationModule {

    @Binds
    @ApplicationContext
    public abstract Context provideContext(Application application);

    @Provides
    @ApplicationScope
    public static PackageInfo packageInfo(@ApplicationContext Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Provides
    @ApplicationScope
    static IPreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }
}
