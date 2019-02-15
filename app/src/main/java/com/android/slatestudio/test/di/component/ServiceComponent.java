package com.android.slatestudio.test.di.component;


import com.android.slatestudio.test.di.module.ServiceModule;
import com.android.slatestudio.test.di.scopes.ServiceScope;
import com.android.slatestudio.test.service.GeofenceTransitionsIntentService;
import dagger.Component;

@ServiceScope
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

    void inject(GeofenceTransitionsIntentService service);
}
