package com.test.lib_common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.test.lib_common.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseMvpFragment extends Fragment implements IBaseView {
    protected List<BasePresenter> mPresenterList;
    public BaseMvpActivity mActivity;
    private Unbinder bind;
    protected View contentView;
    public int mBarHeight;
    private BasePresenter mBasePresenter;
    protected ImmersionBar mImmersionBar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (BaseMvpActivity) context;
    }

    /**
     * 获取宿主Activity
     *
     * @return BaseActivity
     */
    protected BaseMvpActivity getHoldingActivity() {
        return mActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        configPresenter();
        contentView = inflater.inflate(configLayout(), null);
        bind = ButterKnife.bind(this, contentView);
        // ARouter inject 注入
        ARouter.getInstance().inject(this);
        mBarHeight = BarUtils.getStatusBarHeight();
        if (isImmersionBarEnabled()){
            initImmersionBar(true);
        }
        initView();
        initData();
        return contentView;
    }

    protected abstract int configLayout();
    protected abstract void initView();
    protected abstract void initData();

    @Override
    public <P extends BasePresenter> P getPersenter(Class<P> aclass) {
        for (BasePresenter basePresenter:mPresenterList) {
            if (basePresenter.getClass() == aclass) {
                return (P) basePresenter;
            }
        }
        return null;
    }


    @Override
    public void createPersenter(Class<? extends BasePresenter>... classes) {
        if (ObjectUtils.isEmpty(mPresenterList)) {
            mPresenterList = new ArrayList<>();
        }
        for (Class aclass:classes) {
            try {
                mBasePresenter = (BasePresenter) aclass.newInstance();
                mBasePresenter.attachView(this);
                mPresenterList.add(mBasePresenter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar(boolean isChange) {
        //支持当前设备支状态栏字体设置为黑色
        mImmersionBar = ImmersionBar.with(this);
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            if (isChange) {
                mImmersionBar.statusBarDarkFont(true)
                        .fitsSystemWindows(true)
                        .keyboardEnable(true)
                        .init();
            }else {
                mImmersionBar.statusBarDarkFont(false)
                        .keyboardEnable(true)
                        .init();
            }
        }else {
            if (isChange) {
                mImmersionBar.statusBarDarkFont(false)
                        .statusBarColor(R.color.colorPrimary)
                        .fitsSystemWindows(true)
                        .keyboardEnable(true)
                        .init();
            }else {
                mImmersionBar.statusBarDarkFont(false)
                        .keyboardEnable(true)
                        .init();
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (isImmersionBarEnabled()){
            initImmersionBar(true);
        }
    }

    public <T> ObservableTransformer<T,T> setThread(){
        return new ObservableTransformer<T,T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
        if (mPresenterList != null) {
            for (BasePresenter basePresenter : mPresenterList) {
                basePresenter.destory();
            }
        }
    }
}
