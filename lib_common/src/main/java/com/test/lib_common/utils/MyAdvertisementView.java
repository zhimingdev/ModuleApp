package com.test.lib_common.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.test.lib_common.R;

public class MyAdvertisementView extends Dialog {

    private InverstListener inverstListener;
    private DismissListener dismissListener;

    public MyAdvertisementView(Context context) {
        super(context);
        setContentView(R.layout.view_dialog_advertisement);
        //设置点击布局外则Dialog消失
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    public void showDialog() {
        Window window = getWindow();
        //设置弹窗动画
        window.setWindowAnimations(R.style.style_dialog);
        //设置Dialog背景色
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams wl = window.getAttributes();
        //设置弹窗位置
        wl.gravity = Gravity.CENTER;
        window.setAttributes(wl);
        show();
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissListener.onClick(v);
            }
        });
        findViewById(R.id.iv_advertisement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inverstListener.onClick(v);
            }
        });
    }

    public interface InverstListener {
        void onClick(View view);
    }

    public interface DismissListener {
        void onClick(View view);
    }

    public void setInverestListener(InverstListener inverstListener) {
        this.inverstListener = inverstListener;
    }

    public void setDismissListener(DismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }
}
