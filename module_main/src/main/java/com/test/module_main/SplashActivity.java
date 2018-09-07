package com.test.module_main;

import android.os.Handler;

import com.alibaba.android.arouter.launcher.ARouter;
import com.test.lib_common.base.BaseMvpActivity;
import com.test.lib_common.config.Config;
import com.test.module_gesturelock.lock.LockPatternUtils;
import com.test.module_gesturelock.utils.PreferenceCache;

public class SplashActivity extends BaseMvpActivity {

    private LockPatternUtils mLockPatternUtils;
    private boolean mLock;
    private boolean mGestureSwitch;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        mLockPatternUtils = new LockPatternUtils(this);
        mLock = mLockPatternUtils.savedPatternExists();
        mGestureSwitch = PreferenceCache.getGestureSwitch();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mLock && mGestureSwitch) {
                    ARouter.getInstance().build("/gesture/UnlockGesturePasswordActivity")
                            .withString("from","SplashActivity").navigation();
                }else {
                    ARouter.getInstance().build("/main/MainActivity").navigation();
                }
                finish();
            }
        }, Config.SPLASHACTIVITY_POSTDELAYED_TIME);
    }

    @Override
    public void initData() {

    }

    @Override
    public void configPresenter() {

    }

    @Override
    public void finshRefreshComplete(boolean success) {

    }
}
