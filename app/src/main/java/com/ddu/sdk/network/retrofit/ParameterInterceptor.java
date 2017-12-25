package com.ddu.sdk.network.retrofit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yzbzz on 2017/12/22.
 * 添加公共参数
 */
public class ParameterInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("userId", "");
        urlParams.put("token", "");

        String method = request.method();
        if (RetrofitManager.METHOD_GET.equalsIgnoreCase(method)) {
            HttpUrl.Builder httpUrl = request.url().newBuilder();
            Set<Map.Entry<String, String>> entrySet = urlParams.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                httpUrl.addQueryParameter(entry.getKey(), entry.getValue());
            }
            request = request.newBuilder().url(httpUrl.build()).build();
        } else if (RetrofitManager.METHOD_POST.equalsIgnoreCase(method)) {
            RequestBody requestBody = request.body();
            if (requestBody instanceof FormBody) {
                FormBody formBody = (FormBody) requestBody;

                FormBody.Builder formBodyBuilder = new FormBody.Builder();

                int size = formBody.size();
                for (int i = 0; i < size; i++) {
                    formBodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                }

                Set<Map.Entry<String, String>> entrySet = urlParams.entrySet();
                for (Map.Entry<String, String> entry : entrySet) {
                    formBodyBuilder.add(entry.getKey(), entry.getValue());
                }
                request = request.newBuilder().post(formBodyBuilder.build()).build();
            }

        }

        return chain.proceed(request);
    }
}
