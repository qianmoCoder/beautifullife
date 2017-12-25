package com.ddu.sdk.network.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yzbzz on 2017/12/22.
 */

public class HeadersInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .header("User-Agent", "Your-App-Name")
                .header("Accept", "application/vnd.yourapi.v1.full+json")
                .build();
        return chain.proceed(request);
    }
}
