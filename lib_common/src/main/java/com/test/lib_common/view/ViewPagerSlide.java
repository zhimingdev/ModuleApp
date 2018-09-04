package com.test.lib_common.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ViewPagerSlide extends ViewPager {
    public ViewPagerSlide(Context context) {
        super(context);
    }

    public ViewPagerSlide(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //拦截 TouchEvent     返回false 拦截
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }

    //处理 TouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}