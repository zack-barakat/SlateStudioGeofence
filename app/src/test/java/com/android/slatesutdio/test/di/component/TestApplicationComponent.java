package com.android.slatesutdio.test.di.component;

import android.app.Application;
import android.content.Context;
import com.android.slatestudio.test.App;
import com.android.slatestudio.test.data.IDataManager;
import com.android.slatestudio.test.data.prefs.IPreferencesHelper;
import com.android.slatestudio.test.data.repositories.IGeofenceRepository;
import com.android.slatestudio.test.di.component.ApplicationComponent;
import com.android.slatestudio.test.di.qualifiers.ApplicationContext;
import com.android.slatestudio.test.di.scopes.ApplicationScope;
import com.android.slatesutdio.test.di.module.TestApplicationModule;
import com.android.slatesutdio.test.di.module.TestDataManagerModule;
import com.android.slatesutdio.test.mvp.MainPresenterTest;
import com.android.slatesutdio.test.testCase.AppRobolectricTestCase;
import dagger.BindsInstance;
import dagger.Component;

@ApplicationScope
@Component(modules = {
        TestDataManagerModule.class,
        TestApplicationModule.class})
public interface TestApplicationComponent extends ApplicationComponent {

    void inject(App app);

    @ApplicationContext
    Context context();

    void inject(AppRobolectricTestCase appRobolectricTestCase);

    void inject(MainPresenterTest mainPresenterTest);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TestApplicationComponent.Builder application(Application application);

        TestApplicationComponent build();
    }

    Application application();

    IPreferencesHelper getPreferenceHelper();

    IDataManager getDataManager();

    IGeofenceRepository getGeofenceRepository();
}
