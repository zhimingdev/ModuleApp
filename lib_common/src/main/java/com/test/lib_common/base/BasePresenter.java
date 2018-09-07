package com.test.lib_common.base;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BasePresenter<V extends IBaseView> implements IBasePresenter {

    protected WeakReference<V> mViewRef;

    @Override
    public void start() {

    }

    @Override
    public void destory() {

    }

    /**
     * 弱引用view对象.内存不足时回收
     */
    public void attachView(V view) {
        mViewRef = new WeakReference<>(view);
    }

    /**
     * 布局销毁时,清空弱引用对象
     */
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    protected V getView() {
        return mViewRef.get();
    }

    /**
     * 对网络请求进行线程切换
     */
    public <T> ObservableTransformer<T,T> setThread(){
        return new ObservableTransformer<T,T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
