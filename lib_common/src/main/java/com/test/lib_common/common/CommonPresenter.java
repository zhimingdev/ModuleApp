package com.test.lib_common.common;

import com.test.lib_common.base.BasePresenter;

/**
 * 用于需要多次请求的接口,列如获取账户信息
 */

public class CommonPresenter extends BasePresenter<CommonContract.IView> implements CommonContract.IBasePresenter {
    @Override
    public void requestData() {
        getView().refreshView();
    }
}
