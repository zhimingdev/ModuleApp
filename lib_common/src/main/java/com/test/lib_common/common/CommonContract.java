package com.test.lib_common.common;

import com.test.lib_common.base.IBaseView;
/**
 * 用于需要多次请求的接口的契约类
 * 布局刷新的方法可自行添加
 */

public interface CommonContract {
    interface IView extends IBaseView {
        void refreshView();
    }

    interface IBasePresenter {
        void requestData();
    }
}
