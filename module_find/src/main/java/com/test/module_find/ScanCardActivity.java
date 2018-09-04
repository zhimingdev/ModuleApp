/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.test.module_find;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.blankj.utilcode.util.ToastUtils;
import com.test.lib_common.base.BaseMvpActivity;
import com.test.lib_common.utils.FileUtil;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/find/ScanCardActivity")
public class ScanCardActivity extends BaseMvpActivity {
    @BindView(R2.id.idcard_button)
    Button idcardButton;
    @BindView(R2.id.bankcard_button)
    Button bankcardButton;
    private boolean mIsok;
    private static final int REQUEST_CODE_BANKCARD = 111;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan_card;
    }

    @Override
    public void initView() {
        initAccessTokenWithAkSk();
        mIsok = getIntent().getBooleanExtra("isok", false);
    }

    @Override
    public void initData() {

    }

    private boolean checkTokenStatus() {
        if (!mIsok) {
            Toast.makeText(getApplicationContext(), "token还未成功获取", Toast.LENGTH_LONG).show();
        }
        return mIsok;
    }

    /**
     * 用明文ak，sk初始化
     */
    private void initAccessTokenWithAkSk() {
        OCR.getInstance(this).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
                mIsok = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                ToastUtils.showShort("AK，SK方式获取token失败");
            }
        }, getApplicationContext(), "9oiaWSgtvgTcZHVvw8Fjftsg", "luYtSnwdkYszOy7x0tUp2fgMS75ISu34");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initAccessTokenWithAkSk();
        } else {
            ToastUtils.showShort("需要android.permission.READ_PHONE_STATE");
        }
    }

    @Override
    public void configPresenter() {

    }

    @Override
    public void finshRefreshComplete(boolean success) {

    }

    @OnClick({R2.id.idcard_button, R2.id.bankcard_button})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.idcard_button) {
            if (!checkTokenStatus()) {
                return;
            }
            Intent intent = new Intent(ScanCardActivity.this, IDCardActivity.class);
            startActivity(intent);
        }else if (id == R.id.bankcard_button) {
            if (!checkTokenStatus()) {
                return;
            }
            Intent intent = new Intent(ScanCardActivity.this, CameraActivity.class);
            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                    FileUtil.getSaveFile(getApplication()).getAbsolutePath());
            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                    CameraActivity.CONTENT_TYPE_BANK_CARD);
            startActivityForResult(intent, REQUEST_CODE_BANKCARD);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放内存资源
        OCR.getInstance(this).release();
    }
}
