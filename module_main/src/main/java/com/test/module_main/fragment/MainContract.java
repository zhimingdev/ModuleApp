package com.test.module_main.fragment;

import android.content.Context;

import com.test.lib_common.base.IBaseView;
import com.test.module_main.bean.GrilBean;

import java.util.List;

public interface MainContract {

    interface IView extends IBaseView {
        void refreshView(List<GrilBean> results);
    }

    interface IMainPresenter{
        void requestData(Context context);
    }
}
