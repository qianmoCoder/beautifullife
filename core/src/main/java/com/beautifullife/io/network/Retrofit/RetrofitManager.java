package com.beautifullife.io.network.Retrofit;

import android.content.Context;

import com.beautifullife.io.network.okhttp.OKHttpManager;
import com.beautifullife.io.network.okhttp.response.ResponseHandlerInterface;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request.Builder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 创建日期：2015年10月19日 版权所有 悦畅科技有限公司。 保留所有权利。<br>
 * 项目名：悟空停车 - Android客户端<br>
 * 描述：
 *
 * @author admin
 */
public class RetrofitManager {

    public static void createOKhttpClient() {
        OkHttpClient okhttpClient = OKHttpManager.getInstance().getOkHttpClient().clone();
    }

    public static <T> void get(Context context, Call<T> repos, String url, HashMap<String, String> params, String md5Param, boolean isEncode, ResponseHandlerInterface responseHandlerInterface) throws Exception {
        Interceptor interceptor = new Interceptor() {

            @Override
            public com.squareup.okhttp.Response intercept(Chain arg0) throws IOException {
                Builder newRequest = arg0.request().newBuilder();
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("versionName", "1.0");
                headers.put("platform", "android");
                if (headers != null) {
                    for (Map.Entry<String, String> param : headers.entrySet()) {
                        newRequest.addHeader(param.getKey(), param.getValue());
                    }
                }
                return arg0.proceed(newRequest.build());
            }
        };
        OkHttpClient okhttpClient = OKHttpManager.getInstance().getOkHttpClient().clone();
        okhttpClient.interceptors().add(interceptor);

        //RXjava
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(okhttpClient).build();
        repos.enqueue(new Callback<T>() {

            @Override
            public void onResponse(retrofit.Response<T> paramResponse, Retrofit paramRetrofit) {
                //404  paramResponse.errorBody().string()
                Observable.just(paramResponse).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<retrofit.Response<T>>() {

                    @Override
                    public void call(retrofit.Response<T> arg0) {
                        arg0.body();
                    }
                });
            }

            @Override
            public void onFailure(Throwable paramThrowable) {

            }
        });
    }
}
