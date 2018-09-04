package com.test.lib_common.http;

import com.google.gson.GsonBuilder;
import com.test.lib_common.config.Config;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitNewHelper {

    public static RetrofitNewHelper newinstance = null;
    private String type;
    private static final Interceptor logInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    //构建拦截器
    OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logInterceptor)
            .build();
    GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());
    protected Retrofit retrofit;

    public RetrofitNewHelper(String type) {
        this.type = type;
        init(type);

    }

    public static RetrofitNewHelper getNewInstance(String type) {
//        if (newinstance == null) {
            newinstance = new RetrofitNewHelper(type);
//        }

        return newinstance;
    }

    public void init(String type) {
        if (type.equals(Config.DEFAULT)) {
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(Config.BASE_URL2)
                    .addConverterFactory(factory)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }else {
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(Config.BASE_URL)
                    .addConverterFactory(factory)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
    }

    public <T> T create(Class<T> service){
        return retrofit.create(service);
    }
}
