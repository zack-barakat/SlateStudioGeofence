package com.android.slatestudio.test.di.component;


import com.android.slatestudio.test.di.module.ActivityModule;
import com.android.slatestudio.test.di.scopes.ActivityScope;
import com.android.slatestudio.test.ui.base.BaseMvpActivity;
import com.android.slatestudio.test.ui.splash.SplashActivity;
import dagger.Component;


@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(BaseMvpActivity activity);

    void inject(SplashActivity activity);

}