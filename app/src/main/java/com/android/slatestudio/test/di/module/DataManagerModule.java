package com.android.slatestudio.test.di.module;


import com.android.slatestudio.test.data.*;
import com.android.slatestudio.test.data.repositories.GeofenceRepository;
import com.android.slatestudio.test.data.repositories.IGeofenceRepository;
import com.android.slatestudio.test.di.scopes.ApplicationScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by zack_barakat
 */

@Module
public class DataManagerModule {

    @Provides
    @ApplicationScope
    public IDataManager provideDataManager(DataManager manager) {
        return manager;
    }


    @Provides
    @ApplicationScope
    public IEventBusManager provideEventBusHelper(EventBusManager helper) {
        return helper;
    }

    @Provides
    @ApplicationScope
    public RxBus provideRxBus() {
        return new RxBus();
    }

    @Provides
    @ApplicationScope
    public IGeofenceRepository provideGeofenceBusHelper(GeofenceRepository repository) {
        return repository;
    }
}
