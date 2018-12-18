package com.test.module_main.api;

import com.test.lib_common.http.HttpResponse;
import com.test.module_main.bean.GrilBean;
import com.test.module_main.bean.MovieBean;
import com.test.module_main.bean.MovieDetailBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ApiService {

    /**
     * 测试接口,暂时无法使用
     */
    @GET("v2/movie/in_theaters")
    Observable<HttpResponse<List<MovieBean>>> getNews();

    /**
     * 获取电影详情
     *
     * @param id 电影bean里的id
     */
    @GET("v2/movie/subject/{id}")
    Observable<MovieDetailBean> getMovieDetail(@Path("id") String id);

}
