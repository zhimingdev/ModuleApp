package com.test.module_product.api;

import com.test.lib_common.http.HttpResponse;
import com.test.module_product.bean.ResponseGrilsBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductService {

    @GET("福利/10/{number}")
    Observable<HttpResponse<List<ResponseGrilsBean>>> getGirls(@Path("number") int number);
}
