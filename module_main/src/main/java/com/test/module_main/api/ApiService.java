package com.test.module_main.api;

import com.test.lib_common.http.HttpResponse;
import com.test.module_main.bean.GrilBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiService {

    /**
     * 测试接口,暂时无法使用
     */
    @GET("a2a/impressApi/news/mergeList?sign=C7548DE604BCB8A17592EFB9006F9265&pageSize=20&gender=2&ts=1871746850&")
    Observable<HttpResponse<List<GrilBean>>> getNews(@QueryMap Map<String,String> map);

}
