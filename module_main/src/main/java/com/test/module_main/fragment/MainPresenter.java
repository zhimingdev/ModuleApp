package com.test.module_main.fragment;

import android.content.Context;

import com.test.lib_common.base.BasePresenter;
import com.test.lib_common.config.Config;
import com.test.lib_common.http.BaseObserver;
import com.test.lib_common.http.HttpResponse;
import com.test.lib_common.http.RetrofitNewHelper;
import com.test.module_main.api.ApiService;
import com.test.module_main.bean.GrilBean;
import com.test.module_main.bean.MovieBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainPresenter extends BasePresenter<MainContract.IView> implements MainContract.IMainPresenter {

    @Override
    public void requestData(Context context) {
        RetrofitNewHelper.getNewInstance("")
                .create(ApiService.class)
                .getNews()
                .compose(this.<HttpResponse<List<MovieBean>>>setThread())
                .subscribe(new BaseObserver<List<MovieBean>>(context) {
                    @Override
                    public void onSuccess(HttpResponse<List<MovieBean>> t) throws Exception {
                        List<MovieBean> movieBeans = t.getSubjects();
                        getView().refreshView(movieBeans);
                        getView().finshRefreshComplete(true);
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) throws Exception {
                        getView().finshRefreshComplete(false);
                    }
                });
    }
}
