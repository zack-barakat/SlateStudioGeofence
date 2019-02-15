package com.android.slatesutdio.test.testCase;

import android.content.Context;
import android.support.annotation.NonNull;
import com.android.slatestudio.test.data.IDataManager;
import com.android.slatestudio.test.data.IEventBusManager;
import com.android.slatestudio.test.data.prefs.IPreferencesHelper;
import com.android.slatestudio.test.data.repositories.IGeofenceRepository;
import com.android.slatesutdio.test.MockApplication;
import com.android.slatesutdio.test.di.component.TestApplicationComponent;
import com.android.slatesutdio.test.shadows.ShadowPreferenceManager;
import com.android.slatesutdio.test.testRunner.AppRobolectricTestRunner;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import javax.inject.Inject;


@RunWith(AppRobolectricTestRunner.class)
@Config(
        application = MockApplication.class,
        shadows = {ShadowPreferenceManager.class},
        sdk = AppRobolectricTestRunner.DEFAULT_SDK,
        manifest = Config.NONE
)
public abstract class AppRobolectricTestCase extends TestCase {
    private MockApplication application;

    @Inject
    public IDataManager appDataManager;

    @Inject
    public IGeofenceRepository geofenceRepository;

    @Inject
    public IPreferencesHelper preferenceHelper;

    @Inject
    public IEventBusManager eventBusManager;

    @Override
    @Before
    public void setUp() throws Exception {
        component().inject(this);
        MockitoAnnotations.initMocks(this);
        super.setUp();
    }

    protected
    @NonNull
    MockApplication application() {
        if (application != null) {
            return application;
        }

        application = (MockApplication) RuntimeEnvironment.application;
        return application;
    }

    protected
    @NonNull
    TestApplicationComponent component() {
        if (application != null) {
            return (TestApplicationComponent) application.getComponent();
        }

        application = (MockApplication) RuntimeEnvironment.application;
        return (TestApplicationComponent) application.getComponent();
    }

    protected
    @NonNull
    Context context() {
        return application().getApplicationContext();
    }
}
