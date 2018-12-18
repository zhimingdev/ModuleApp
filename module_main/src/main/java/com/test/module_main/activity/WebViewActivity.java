package com.test.module_main.activity;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.test.lib_common.base.BaseMvpActivity;
import com.test.module_main.R;

public class WebViewActivity extends BaseMvpActivity {

    private WebView webView;
    private String url;

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView() {
        webView = (WebView) findViewById(R.id.web);
        url = getIntent().getStringExtra("url");
    }

    @Override
    public void initData() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        webView.loadUrl(url);
    }

    @Override
    public void configPresenter() {

    }

    @Override
    public void finshRefreshComplete(boolean success) {

    }
}
