package com.lib.frame.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import com.chia.global.Global;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @Description ViewModel基类
 * Created by EthanCo on 2016/6/13.
 */
public abstract class BaseViewModel<T> extends AndroidViewModel {

    protected Reference<T> mViewRef; //View接口类型的弱引用

    public BaseViewModel() {
        super((Application) Global.getContext());
    }

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    @CallSuper
    public void attachView(T view) { //建立关联
        mViewRef = new WeakReference<T>(view);
    }

    @NonNull
    protected T getView() {
        if (mViewRef == null) return getEmptyView();

        T t = mViewRef.get();
        return t == null ? getEmptyView() : t;
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    @CallSuper
    public void detachView() {

        //ZAnnoInjector.inject(this);

        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        detachView();
    }

    protected T getEmptyView() {
        return null;
    }
}
