package com.test.lib_common.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ObjectUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.test.lib_common.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseMvpActivity extends AppCompatActivity implements IBaseView{
    private Unbinder unbinder;
    protected Context mContext;
    protected List<BasePresenter> mPresenterList;
    private BasePresenter mBasePresenter;
    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        configPresenter();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mContext = this;
        unbinder = ButterKnife.bind(this);
        // 禁止所有activity横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // ARouter inject 注入
        ARouter.getInstance().inject(this);
        //初始化沉浸式
        if (isImmersionBarEnabled())
            initImmersionBar(false);
        initView();
        initData();
    }
    //初始化布局
    public abstract int getLayoutId();
    //初始化控件
    public abstract void initView();
    //初始化数据
    public abstract void initData();

    /**
     * 获取Presenter
     * @param aclass p.getaclass
     */
    @Override
    public <P extends BasePresenter> P getPersenter(Class<P> aclass) {
        for (BasePresenter basePresenter:mPresenterList) {
            if (basePresenter.getClass() == aclass) {
                return (P) basePresenter;
            }
        }
        return null;
    }

    /**
     * 创建Presenter
     * 可以创建多个Presenter
     */
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
     * @param isChange 是否使用沉浸式
     */
    protected void initImmersionBar(boolean isChange) {
        //支持当前设备支状态栏字体设置为黑色
        mImmersionBar = ImmersionBar.with(this);
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            if (isChange) {
                mImmersionBar.statusBarDarkFont(false)
                        .keyboardEnable(true)
                        .init();
            }else {
                mImmersionBar.statusBarDarkFont(true)
                        .fitsSystemWindows(true)
                        .keyboardEnable(true)
                        .init();
            }
        }else {
            if (isChange) {
                mImmersionBar.statusBarDarkFont(false)
                        .fitsSystemWindows(true)
                        .keyboardEnable(true)
                        .init();
            }else {
                mImmersionBar.statusBarDarkFont(false)
                        .statusBarColor(R.color.colorPrimary)
                        .fitsSystemWindows(true)
                        .keyboardEnable(true)
                        .init();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mImmersionBar != null) {
            //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态   }
            mImmersionBar.destroy();
        }
        if (mPresenterList != null) {
           for (BasePresenter basePresenter : mPresenterList) {
               basePresenter.destory();
               basePresenter.detachView();
           }
        }
    }
}
