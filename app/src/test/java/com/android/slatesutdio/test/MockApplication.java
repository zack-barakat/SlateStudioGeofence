package com.android.slatesutdio.test;

import com.android.slatestudio.test.App;
import com.android.slatesutdio.test.di.component.DaggerTestApplicationComponent;
import com.android.slatesutdio.test.di.component.TestApplicationComponent;

public class MockApplication extends App {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void initDagger() {
        TestApplicationComponent applicationComponent = DaggerTestApplicationComponent.builder()
                .application(this)
                .build();
        setComponent(applicationComponent);
    }
}
