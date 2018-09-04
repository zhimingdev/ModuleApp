package com.test.module_gesturelock.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.test.lib_common.base.BaseMvpActivity;
import com.test.module_gesturelock.R;
import com.test.module_gesturelock.lock.LockPatternUtils;
import com.test.module_gesturelock.utils.PreferenceCache;

@Route(path = "/gesturelock/GestureLockActivity")
public class GestureLockActivity extends BaseMvpActivity implements View.OnClickListener {

    private ImageView mIvHandSwitch;
    private LinearLayout mlinearLayputSetting;
    private View mView;
    private LockPatternUtils mLockPatternUtils;

    @Override
    public int getLayoutId() {
        return R.layout.activity_gesture_activity;
    }

    @Override
    public void initView() {
        mIvHandSwitch = (ImageView) findViewById(R.id.iv_hand_switch);
        mIvHandSwitch.setOnClickListener(this);
        mlinearLayputSetting = (LinearLayout) findViewById(R.id.ll_setting_hand);
        mlinearLayputSetting.setOnClickListener(this);
        mView = findViewById(R.id.view_second);
        mLockPatternUtils = new LockPatternUtils(this);
    }

    @Override
    public void initData() {
        if (PreferenceCache.getGestureSwitch()) {
            mIvHandSwitch.setImageResource(R.mipmap.auto_bidding_off);
            mlinearLayputSetting.setVisibility(View.VISIBLE);
            mView.setVisibility(View.VISIBLE);
        }else {
            mIvHandSwitch.setImageResource(R.mipmap.auto_bidding_on);
            mlinearLayputSetting.setVisibility(View.GONE);
            mView.setVisibility(View.GONE);
        }
    }

    @Override
    public void configPresenter() {

    }

    @Override
    public void finshRefreshComplete(boolean success) {

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_hand_switch) {
            if (PreferenceCache.getGestureSwitch()) {
                //开的状态
                PreferenceCache.putGestureSwitch(false);
                initData();
            } else {
                //关的状态
                if (mLockPatternUtils.savedPatternExists()) {
                    PreferenceCache.putGestureSwitch(true);
                    initData();
                } else {
                    Intent intent = new Intent(GestureLockActivity.this, CreateGesturePasswordActivity.class);
                    startActivity(intent);
                }
            }

        } else if (i == R.id.ll_setting_hand) {
            ARouter.getInstance().build("/gesture/UnlockGesturePasswordActivity")
                    .withString("from","GestureLockActivity").navigation();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
