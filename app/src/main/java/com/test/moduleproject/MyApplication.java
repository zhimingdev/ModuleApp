package com.test.moduleproject;

import com.alibaba.android.arouter.launcher.ARouter;
import com.test.lib_common.base.BaseApplication;

public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
//        if (Utils.isAppDebug()) {
//            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
//            ARouter.openDebug();
//            ARouter.openLog();
//        }
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);
    }
}
