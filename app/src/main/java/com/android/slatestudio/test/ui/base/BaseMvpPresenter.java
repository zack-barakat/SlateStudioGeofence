package com.android.slatestudio.test.ui.base;

import android.content.Context;
import com.android.slatestudio.test.data.IDataManager;
import com.android.slatestudio.test.data.IEventBusManager;
import com.android.slatestudio.test.di.qualifiers.ApplicationContext;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import java.lang.ref.WeakReference;

public abstract class BaseMvpPresenter<V extends BaseView> implements BasePresenter<V> {

    @ApplicationContext
    protected final Context mAppContext;
    protected IDataManager mDataManager;
    protected final IEventBusManager mEventBusManager;
    protected CompositeDisposable disposableSubscription = new CompositeDisposable();
    WeakReference<V> mViewWeak;

    boolean isFirstTime = true;
    private boolean isViewPaused;

    public BaseMvpPresenter(IDataManager dataManager) {
        mDataManager = dataManager;
        this.mAppContext = mDataManager.getApplicationContext();
        this.mEventBusManager = mDataManager.getEventBusManager();
    }

    @Override
    public void onAttachView(V view) {
        mViewWeak = new WeakReference<>(view);
        if (isFirstTime) {
            onFirstViewAttach();
            isFirstTime = false;
        } else {
            onRestoreState();
        }
    }

    protected void onRestoreState() {

    }

    /**
     * This method is called once only no matter how times the view gets recreated. useful for tracking
     */
    protected void onFirstViewAttach() {

    }

    public void addToSubscription(Disposable disposable) {
        disposableSubscription.add(disposable);
    }


    protected <T> void subscribeToEventBus(Class<T> clazz, Consumer<T> next) {
        Disposable disposable = mEventBusManager.getBus().register(clazz, next);
        addToSubscription(disposable);
    }

    @Override
    public void onResume() {

    }

    @Override
    public boolean isViewPaused() {
        return isViewPaused;
    }

    @Override
    public void setViewPaused(boolean viewPaused) {
        this.isViewPaused = viewPaused;
    }

    @NonNull
    public V getView() {
        return mViewWeak.get();
    }

    @Override
    public void onDestroyView() {
        disposableSubscription.clear();
    }
}
