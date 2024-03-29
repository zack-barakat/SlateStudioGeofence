package com.android.slatestudio.test.di.module;

import android.app.Activity;
import android.content.Context;
import com.android.slatestudio.test.di.qualifiers.ActivityContext;
import com.android.slatestudio.test.ui.creategeofence.CreateGeofenceContracts;
import com.android.slatestudio.test.ui.creategeofence.CreateGeofencePresenter;
import com.android.slatestudio.test.ui.main.MainContracts;
import com.android.slatestudio.test.ui.main.MainPresenter;
import com.android.slatestudio.test.ui.splash.SplashContracts;
import com.android.slatestudio.test.ui.splash.SplashPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }

    @Provides
    Activity provideActivity() {
        return activity;
    }

    @Provides
    SplashContracts.Presenter<SplashContracts.View> provideSplashPresenter(SplashPresenter splashPresenter) {
        return splashPresenter;
    }

    @Provides
    MainContracts.Presenter<MainContracts.View> provideMainPresenter(MainPresenter mainPresenter) {
        return mainPresenter;
    }

    @Provides
    CreateGeofenceContracts.Presenter<CreateGeofenceContracts.View> provideCreateGeofencePresenter(CreateGeofencePresenter geofencePresenter) {
        return geofencePresenter;
    }
}
