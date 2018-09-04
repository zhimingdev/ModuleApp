package com.test.module_gesturelock.utils;

import com.blankj.utilcode.util.SPUtils;

public class PreferenceCache {
    // 判断是否设置有手势密码
    public static final String GESTURE_FLG = "gesture_flg";
    // 手势密码开关
    public static final String GESTURE_SWITCH = "gesture_time";

    public static void putGestureFlag(boolean flg) {
        SPUtils.getInstance().put(GESTURE_FLG,flg);
    }

    public static boolean getGestureFlag() {
        return SPUtils.getInstance().getBoolean(GESTURE_FLG,false);
    }

    public static void putGestureSwitch(boolean flg) {
        SPUtils.getInstance().put(GESTURE_SWITCH,flg);
    }

    public static boolean getGestureSwitch() {
        return SPUtils.getInstance().getBoolean(GESTURE_SWITCH,false);
    }
}
