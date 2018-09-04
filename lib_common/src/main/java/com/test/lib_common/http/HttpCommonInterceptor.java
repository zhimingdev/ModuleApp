package com.test.lib_common.http;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 自定义拦截器,数据排序,加密
 */

public class HttpCommonInterceptor implements Interceptor {

    private Map<String,String> mHeaderParamsMap = new HashMap<>();
    private JSONObject json;

    public HttpCommonInterceptor() {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        // 添加新的参数，添加到url 中
       /* HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host());*/
        HttpUrl hostUrl = oldRequest.url();
        String url = hostUrl.encodedPath();

//        MediaType mediaType = oldRequest.body().contentType();
//        try {
//            Field field = mediaType.getClass().getDeclaredField("mediaType");
//            field.setAccessible(true);
//            field.set(mediaType, "application/json");
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }

        /**
         * 参数进行AES,BASE64加密(字典排序)
         */
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        json = new JSONObject();
//
//        if(oldRequest.body() instanceof FormBody) {
//            FormBody oldFormBody = (FormBody) oldRequest.body();
//            Map<String, String> map = new TreeMap<String, String>();
//            String body = "";
//            for (int i = 0; i < oldFormBody.size(); i++) {
//                String encodedName = oldFormBody.encodedName(i);
//                String encodedValue = oldFormBody.encodedValue(i);
//                map.put(encodedName, encodedValue);
//            }
//
//            List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(map.entrySet());
//            //然后通过比较器来实现排序
//            Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
//                //升序排序
//                public int compare(Map.Entry<String, String> o1,
//                                   Map.Entry<String, String> o2) {
//                    return o1.getKey().toLowerCase().compareTo(o2.getKey().toLowerCase());
//                }
//
//            });
//
//            Map<String,String> map1 = new TreeMap<>();
//            for (int i = 0; i < list.size(); i++) {
//                String key = list.get(i).getKey();
//                String value = list.get(i).getValue();
//                map1.put(key,value);
//                if (i == oldFormBody.size() - 1) {
//                    body += (key + "=" + value);
//                } else {
//                    body += (key + "=" + value + "&");
//                }
//            }
//            String key = SpUtils.getString(AndroidApplication.getInstance(), "KEY");
//            String encodes = null;
//            try {
//                encodes = Des.encode(key, new Gson().toJson(map1));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            long l = System.currentTimeMillis();
//            String s = Base64s.base64_encode(Md5.encodes(url + AppUtils.getIMEI(AndroidApplication.getInstance()) + new Gson().toJson(map1) + "null" + "1516766784399") + "," + "1516766784399");
//            try {
//                json.put("params",encodes);
//                json.put("sign",s);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));


        // 新的请求
        Request.Builder requestBuilder =  oldRequest.newBuilder();
        requestBuilder.method(oldRequest.method(), oldRequest.body());
        //添加公共参数,添加到header中
        if(mHeaderParamsMap.size() > 0){
            for(Map.Entry<String,String> params:mHeaderParamsMap.entrySet()){
                requestBuilder.header(params.getKey(),params.getValue());
            }
        }
        Request newRequest = requestBuilder.build();
        Response response = chain.proceed(newRequest);
        Log.i("OkHttp",new Gson().toJson(response.body()));
        System.out.println("-----"+new Gson().toJson(response));
        return chain.proceed(newRequest);
    }

    public static class Builder{
        HttpCommonInterceptor mHttpCommonInterceptor;

        public Builder(){
            mHttpCommonInterceptor = new HttpCommonInterceptor();
        }

        public Builder addHeaderParams(String key, String value){
            mHttpCommonInterceptor.mHeaderParamsMap.put(key,value);
            return this;
        }

        public Builder  addHeaderParams(String key, int value){
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder  addHeaderParams(String key, float value){
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder  addHeaderParams(String key, long value){
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder  addHeaderParams(String key, double value){
            return addHeaderParams(key, String.valueOf(value));
        }


        public HttpCommonInterceptor build(){
            return mHttpCommonInterceptor;
        }
    }
}
