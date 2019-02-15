package com.android.slatesutdio.test.di.module;

import com.android.slatestudio.test.data.*;
import com.android.slatestudio.test.data.repositories.GeofenceRepository;
import com.android.slatestudio.test.data.repositories.IGeofenceRepository;
import com.android.slatestudio.test.di.scopes.ApplicationScope;
import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.spy;


@Module
public class TestDataManagerModule {

    @Provides
    @ApplicationScope
    IDataManager provideIAppDataManager(DataManager testDataManager) {
        return spy(testDataManager);
    }

    @Provides
    @ApplicationScope
    public RxBus provideRxBus() {
        return new RxBus();
    }

    @Provides
    @ApplicationScope
    public IEventBusManager provideEventBusManager(EventBusManager manager) {
        return manager;
    }


    @Provides
    @ApplicationScope
    IGeofenceRepository provideIGeofenceRepository(GeofenceRepository geofenceRepository) {
        return spy(geofenceRepository);
    }
}
