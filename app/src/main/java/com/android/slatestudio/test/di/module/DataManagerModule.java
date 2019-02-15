package com.android.slatestudio.test.di.module;


import com.android.slatestudio.test.data.DataManager;
import com.android.slatestudio.test.data.IDataManager;
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
}
