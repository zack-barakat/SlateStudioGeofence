package com.android.slatestudio.test.di.component;

import android.app.Application;
import android.content.Context;
import com.android.slatestudio.test.App;
import com.android.slatestudio.test.data.IDataManager;
import com.android.slatestudio.test.data.IEventBusManager;
import com.android.slatestudio.test.di.module.ApplicationModule;
import com.android.slatestudio.test.di.module.DataManagerModule;
import com.android.slatestudio.test.di.qualifiers.ApplicationContext;
import com.android.slatestudio.test.di.scopes.ApplicationScope;
import dagger.BindsInstance;
import dagger.Component;

@ApplicationScope
@Component(modules = {
        DataManagerModule.class,
        ApplicationModule.class})
public interface ApplicationComponent {

    void inject(App app);

    @ApplicationContext
    Context context();

    Application application();

    IDataManager getDataManager();

    IEventBusManager getEventBusManager();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }
}