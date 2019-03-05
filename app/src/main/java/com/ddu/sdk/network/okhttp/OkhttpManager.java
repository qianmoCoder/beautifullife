package com.ddu.sdk.network.okhttp;

import com.ddu.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by yzbzz on 2018/9/3.
 */
public class OkhttpManager {

    // 添加日志打印
    public void addLogging() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

        HttpLoggingInterceptor.Level level;
        if (BuildConfig.DEBUG) {
            level = HttpLoggingInterceptor.Level.BODY;
        } else {
            level = HttpLoggingInterceptor.Level.NONE;
        }
        httpLoggingInterceptor.setLevel(level);

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.addInterceptor(httpLoggingInterceptor);
    }
}
