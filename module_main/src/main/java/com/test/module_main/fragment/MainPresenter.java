package com.test.module_main.fragment;

import android.content.Context;

import com.test.lib_common.base.BasePresenter;
import com.test.lib_common.config.Config;
import com.test.lib_common.http.BaseObserver;
import com.test.lib_common.http.HttpResponse;
import com.test.lib_common.http.RetrofitNewHelper;
import com.test.module_main.api.ApiService;
import com.test.module_main.bean.GrilBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainPresenter extends BasePresenter<MainContract.IView> implements MainContract.IMainPresenter {

    @Override
    public void requestData(Context context) {
        Map<String, String> map = new HashMap<>();
        map.put("page", ""+ Config.CURRENTPAGE);
        RetrofitNewHelper.getNewInstance("")
                .create(ApiService.class)
                .getNews(map)
                .compose(this.<HttpResponse<List<GrilBean>>>setThread())
                .subscribe(new BaseObserver<List<GrilBean>>(context) {
                    @Override
                    public void onSuccess(HttpResponse<List<GrilBean>> t) throws Exception {
                        List<GrilBean> grilBeans = t.getData();
                        getView().refreshView(grilBeans);
                        getView().finshRefreshComplete(true);
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) throws Exception {
                        getView().finshRefreshComplete(false);
                    }
                });
    }
}
