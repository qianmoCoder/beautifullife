package com.ddu.sdk.network.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yzbzz on 2017/12/22.
 */

public class RetrofitManager {

    public final static String METHOD_GET = "GET";
    public final static String METHOD_POST = "POST";

    private OkHttpClient mOkhttpClient;

    private RetrofitManager() {

    }

    public static RetrofitManager getInstance() {
        return RetrofitManager.SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static RetrofitManager instance = new RetrofitManager();
    }

    public OkHttpClient getOkHttpClient() {
        if (null == mOkhttpClient) {
            mOkhttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new HeadersInterceptor())
                    .addInterceptor(new MoreBaseUrlInterceptor())
                    .addInterceptor(new ParameterInterceptor())
                    .build();
        }
        return mOkhttpClient;
    }

    public Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
