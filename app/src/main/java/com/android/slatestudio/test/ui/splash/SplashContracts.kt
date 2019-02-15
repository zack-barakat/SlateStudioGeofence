package com.android.slatestudio.test.ui.splash

import com.android.slatestudio.test.ui.base.BasePresenter
import com.android.slatestudio.test.ui.base.BaseView

interface SplashContracts {

    interface View : BaseView {
        fun showMainScreen()
    }

    interface Presenter<V : View> : BasePresenter<V>
}
