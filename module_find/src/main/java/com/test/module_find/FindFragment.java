package com.test.module_find;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.bigkoo.alertview.AlertView;
import com.blankj.utilcode.util.ToastUtils;
import com.test.lib_common.base.BaseMvpFragment;
import com.test.lib_common.view.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class FindFragment extends BaseMvpFragment {
    @BindView(R2.id.ll_find)
    LinearLayout llFind;
    @BindView(R2.id.rl_photo)
    RelativeLayout rlPhoto;
    @BindView(R2.id.rl_card)
    RelativeLayout rlCard;
    @BindView(R2.id.fragment_find_titlebar)
    TitleBar fragmentFindTitlebar;
    @BindView(R2.id.rl_gesturelock)
    RelativeLayout rlGesturelock;

    protected boolean hasGotToken = false;

    public static FindFragment newInstance() {
        return new FindFragment();
    }

    @Override
    protected int configLayout() {
        return R.layout.fragment_find;
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
        fragmentFindTitlebar.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertView("提示", "主要是实现相关功能的测试,以及案例", null, new String[]{"确定"}, null, mActivity,
                        AlertView.Style.Alert, null).show();

//                final RxDialogSure rxDialogSure = new RxDialogSure(mActivity);
//                rxDialogSure.setContent("主要是实现相关功能的测试,以及案例");
//                rxDialogSure.setTitle("提示");
//                rxDialogSure.getSureView().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        rxDialogSure.cancel();
//                    }
//                });
//                rxDialogSure.show();
            }
        });
    }

    @Override
    public void configPresenter() {

    }

    @Override
    public void finshRefreshComplete(boolean success) {

    }


    @OnClick({R2.id.rl_photo, R2.id.rl_card,R2.id.rl_gesturelock})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.rl_photo) {
            ARouter.getInstance().build("/find/PhotoActivity").navigation();
        } else if (id == R.id.rl_card) {
            ARouter.getInstance().build("/find/ScanCardActivity").withBoolean("isok", hasGotToken).navigation();
        }else if (id == R.id.rl_gesturelock) {
            ARouter.getInstance().build("/gesturelock/GestureLockActivity").navigation();
        }
    }

    @Override
    protected void initImmersionBar(boolean isChange) {
        super.initImmersionBar(true);
    }

}
