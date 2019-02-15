package com.android.slatestudio.test.data

import android.os.Handler
import android.os.Looper
import com.android.slatestudio.test.data.busevent.FatalAppErrorEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class RxBus {

    private val mBusSubject = PublishSubject.create<Any>()

    fun <T> register(eventClass: Class<T>, onNext: Consumer<T>): Disposable {
        return mBusSubject
            .filter { event -> event != null }
            .filter { event -> event.javaClass == eventClass }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .map { obj -> obj as T }
            .onErrorReturn { throwable ->
                Handler(Looper.getMainLooper()).postDelayed({ post(FatalAppErrorEvent(throwable)) }, 100)
                null
            }
            .subscribe(onNext, Consumer<Throwable> {

            })
    }

    fun <T> register(eventClass: Class<T>, onNext: Consumer<T>, onError: Consumer<Throwable>): Disposable {
        return mBusSubject
            .filter { event -> event.javaClass == eventClass }
            .map { obj -> obj as T }
            .subscribe(onNext, onError)
    }

    fun post(event: Any) {
        mBusSubject.onNext(event)
    }

}