package com.android.slatestudio.test.di.module;

import android.app.Service;
import android.content.Context;
import com.android.slatestudio.test.di.qualifiers.ServiceContext;
import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    private final Service service;

    public ServiceModule(Service service) {
        this.service = service;
    }

    @Provides
    public Service provideService() {
        return service;
    }

    @Provides
    @ServiceContext
    public Context provideServiceContext() {
        return service;
    }
}
