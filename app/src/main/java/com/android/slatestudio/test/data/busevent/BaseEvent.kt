package com.android.slatestudio.test.data.busevent

abstract class BaseEvent(isSuccess: Boolean, error: Throwable?) {
    var isSuccess: Boolean = false
        protected set
    var error: Throwable?
        protected set

    init {
        this.isSuccess = isSuccess
        this.error = error
    }
}
