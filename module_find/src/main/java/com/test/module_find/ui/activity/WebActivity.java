package com.test.module_find.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.test.lib_common.base.BaseMvpActivity;
import com.test.lib_common.dialog.RxDialogShapeLoading;
import com.test.module_find.R;
import com.test.module_find.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends BaseMvpActivity {

    @BindView(R2.id.wv_find)
    WebView wvFind;
    private String url;
    private RxDialogShapeLoading rxDialogShapeLoading;

    @Override
    public int getLayoutId() {
        return R.layout.find_activity_webview;
    }

    @Override
    public void initView() {
        url = getIntent().getStringExtra("url");
    }

    @Override
    public void initData() {
        rxDialogShapeLoading = new RxDialogShapeLoading(mContext);
        rxDialogShapeLoading.setLoadingText("加载中...");
        wvFind.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                rxDialogShapeLoading.dismiss();
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                rxDialogShapeLoading.show();
                super.onPageStarted(view, url, favicon);
            }
        });
        wvFind.loadUrl(url);
    }

    @Override
    public void configPresenter() {

    }

    @Override
    public void finshRefreshComplete(boolean success) {

    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return super.isImmersionBarEnabled();
    }

}

