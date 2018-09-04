package com.test.module_gesturelock.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.test.lib_common.base.BaseMvpActivity;
import com.test.module_gesturelock.R;
import com.test.module_gesturelock.R2;
import com.test.module_gesturelock.lock.LockPatternUtils;
import com.test.module_gesturelock.lock.LockPatternView;
import com.test.module_gesturelock.utils.PreferenceCache;

import java.util.List;

import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/3.
 */
@Route(path = "/gesture/UnlockGesturePasswordActivity")
public class UnlockGesturePasswordActivity extends BaseMvpActivity {

    private TextView gesturepwd_unlock_forget;
    private LockPatternView mLockPatternView;
    private int mFailedPatternAttemptsSinceLastTimeout = 0;
    private CountDownTimer mCountdownTimer = null;
    private TextView mHeadTextView,mHeadPhone;
    private Toast mToast;
    private String phoneNumber ="";
    private RelativeLayout rl_ges;
    private LockPatternUtils mLockPatternUtils;
    private boolean mASwitch;
    private String mFrom;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    public int getLayoutId() {
        return R.layout.activity_gesturepassword_unlock;
    }

    @Override
    public void initView() {
        mFrom = getIntent().getStringExtra("from");
        rl_ges = (RelativeLayout) findViewById(R.id.rl_more_ges);
        gesturepwd_unlock_forget = (TextView) findViewById(R.id.gesturepwd_unlock_forget);
        mLockPatternView = (LockPatternView) this
                .findViewById(R.id.gesturepwd_unlock_lockview);
        mHeadTextView = (TextView) findViewById(R.id.gesturepwd_unlock_text);
        mHeadPhone = (TextView) findViewById(R.id.gesturepwd_unlock_phone);
        mLockPatternView.setOnPatternListener(mChooseNewLockPatternListener);
        mLockPatternView.setTactileFeedbackEnabled(true);
        mLockPatternUtils = new LockPatternUtils(this);
        mHeadViewInit();
        mASwitch = PreferenceCache.getGestureSwitch();
    }

    @Override
    public void initData() {
    }


    @OnClick({R2.id.gesturepwd_unlock_forget})
    public void onClick(View v){
        int i = v.getId();
        if (i == R.id.gesturepwd_unlock_forget) {
            mLockPatternUtils.clearLock();
            PreferenceCache.putGestureSwitch(false);
            finish();
        }
    }
    private Runnable mClearPatternRunnable = new Runnable() {
        public void run() {
            mLockPatternView.clearPattern();
        }
    };

    protected LockPatternView.OnPatternListener mChooseNewLockPatternListener = new LockPatternView.OnPatternListener() {

        public void onPatternStart() {
            mLockPatternView.removeCallbacks(mClearPatternRunnable);
            patternInProgress();
        }

        public void onPatternCleared() {
            mLockPatternView.removeCallbacks(mClearPatternRunnable);
        }

        public void onPatternDetected(List<LockPatternView.Cell> pattern) {
            if (pattern == null)
                return;
            if (mLockPatternUtils.checkPattern(pattern)) {
                if ("SplashActivity".equals(mFrom)) {
                    UnlockGesturePasswordActivity.this.finish();
                    ARouter.getInstance().build("/main/MainActivity").navigation();
                    finish();
                }else {
                    mLockPatternView
                            .setDisplayMode(LockPatternView.DisplayMode.Correct);
                    showToast("解锁成功,可重新绘制手势");
                    UnlockGesturePasswordActivity.this.finish();
                    Intent intent = new Intent(
                            UnlockGesturePasswordActivity.this,
                            CreateGesturePasswordActivity.class);
                    // 打开新的Activity
                    startActivity(intent);
                }
            } else {
                mLockPatternView
                        .setDisplayMode(LockPatternView.DisplayMode.Wrong);
                mFailedPatternAttemptsSinceLastTimeout++;
                int retry = LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT
                        - mFailedPatternAttemptsSinceLastTimeout;
                if (retry >= 0) {
                    if (retry == 0) {
                        showToast("您已5次输错密码");
                        mLockPatternUtils.clearLock();
                        PreferenceCache.putGestureSwitch(false);
                        finish();
                    }
                    mHeadTextView.setText("密码错误，还可以再输入" + retry + "次");
                    mHeadTextView.setTextColor(Color.RED);
                    postClearPatternRunnable();

                }
            }
        }

        public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {

        }

        private void patternInProgress() {
        }
    };

    private void backSettingActivity() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //backSettingActivity();
            PreferenceCache.putGestureSwitch(mASwitch);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    private void mHeadViewInit() {
        if (TextUtils.isEmpty(phoneNumber)) {
            mHeadTextView.setText("请绘制解锁密码");
            mHeadTextView.setTextColor(Color.parseColor("#ff6600"));
        } else {
            mHeadTextView.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    private void postClearPatternRunnable() {
        mLockPatternView.removeCallbacks(mClearPatternRunnable);
        mLockPatternView.postDelayed(mClearPatternRunnable, 2000);
    }


    private void showToast(CharSequence message) {
        if (null == mToast) {
            mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountdownTimer != null)
            mCountdownTimer.cancel();
    }

    @Override
    public void configPresenter() {

    }

    @Override
    public void finshRefreshComplete(boolean success) {

    }
}
