package com.beautifullife.core.network.okhttp.request;

import com.beautifullife.core.network.okhttp.response.ProgressHttpResponseHandler;
import com.beautifullife.core.network.okhttp.response.ProgressResponseBody;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;

/**
 * Created by admin on 2015/11/13.
 */
public class OKHttpDownloadRequest extends OKHttpRequest {

    private OKHttpDownloadRequest(Builder builder) {
        super(builder);
    }

    public static class Builder extends OKHttpRequest.Builder<Builder> {

        @Override
        public OKHttpDownloadRequest build() {
            return new OKHttpDownloadRequest(this);
        }
    }

    @Override
    public Request buildRequest() {
        Request.Builder builder = getBuilder();

        String url = getUrl();
        Map<String, String> params = getParams();

        if (params != null && !params.isEmpty()) {
            url = appendUrl(url, params);
        }

        builder.url(url);
        return builder.build();
    }

    public static Builder create() {
        return new Builder();
    }

    protected void request(ProgressHttpResponseHandler responseHandlerInterface) throws IOException {
        final ProgressHttpResponseHandler progressHttpResponseHandler = responseHandlerInterface;
        OkHttpClient client = okHttpManager.getOkHttpClient().clone();
        client.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                // 拦截
                Response originalResponse = chain.proceed(chain.request());
                // 包装响应体并返回
                ProgressResponseBody responseBody = new ProgressResponseBody(originalResponse.body(), progressHttpResponseHandler);
                return originalResponse.newBuilder().body(responseBody).build();
            }
        });
        Call call = client.newCall(buildRequest());
        okHttpManager.sendRequest(call, responseHandlerInterface);

    }
}
