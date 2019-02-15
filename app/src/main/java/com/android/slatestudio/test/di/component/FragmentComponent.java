package com.android.slatestudio.test.di.component;

import com.android.slatestudio.test.di.module.FragmentModule;
import com.android.slatestudio.test.di.scopes.FragmentScope;
import com.android.slatestudio.test.ui.base.BaseMvpFragment;
import dagger.Component;


@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(BaseMvpFragment baseMvpFragment);

}