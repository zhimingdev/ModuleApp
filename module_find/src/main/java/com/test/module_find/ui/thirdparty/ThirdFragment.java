package com.test.module_find.ui.thirdparty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.blankj.utilcode.util.ToastUtils;
import com.test.lib_common.base.BaseMvpFragment;
import com.test.module_find.R;
import com.test.module_find.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ThirdFragment extends BaseMvpFragment {
    protected boolean hasGotToken = false;
    @BindView(R2.id.rl_photo)
    RelativeLayout rlPhoto;
    @BindView(R2.id.rl_card)
    RelativeLayout rlCard;
    @BindView(R2.id.rl_gesturelock)
    RelativeLayout rlGesturelock;

    public static ThirdFragment newInstance() {
        ThirdFragment thirdFragment = new ThirdFragment();
        Bundle bundle = new Bundle();
        thirdFragment.setArguments(bundle);
        return thirdFragment;
    }

    @Override
    protected int configLayout() {
        return R.layout.third_fragment;
    }

    @Override
    protected void initView() {
        OCR.getInstance(mActivity).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                ToastUtils.showShort("AK，SK方式获取token失败");
            }
        }, mActivity, "9oiaWSgtvgTcZHVvw8Fjftsg", "luYtSnwdkYszOy7x0tUp2fgMS75ISu34");
    }

    @Override
    protected void initData() {
    }

    @Override
    public void configPresenter() {

    }

    @Override
    public void finshRefreshComplete(boolean success) {

    }

    @OnClick({R2.id.rl_photo, R2.id.rl_card, R2.id.rl_gesturelock})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.rl_photo) {
            ARouter.getInstance().build("/find/PhotoActivity").navigation();
        } else if (id == R.id.rl_card) {
            ARouter.getInstance().build("/find/ScanCardActivity").withBoolean("isok", hasGotToken).navigation();
        } else if (id == R.id.rl_gesturelock) {
            ARouter.getInstance().build("/gesturelock/GestureLockActivity").navigation();
        }
    }
}
