package com.beautifullife.core.network.okhttp.request;

import com.beautifullife.core.network.okhttp.OKHttpManager;
import com.beautifullife.core.network.okhttp.response.ResponseHandlerInterface;
import com.beautifullife.core.util.CollectionUtil;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;

public abstract class OKHttpRequest {

    private final String url;
    private final Object tag;
    private final Map<String, String> headers;
    private final Map<String, String> params;

    protected OKHttpManager okHttpManager;

    protected OKHttpRequest(Builder builder) {
        this.url = builder.url;
        this.tag = builder.tag;
        this.headers = builder.headers;
        this.params = builder.params;
        okHttpManager = OKHttpManager.getInstance();
    }

    public abstract static class Builder<T extends Builder> {

        private String url;
        private Object tag;
        private Map<String, String> headers;
        private Map<String, String> params;

        public T url(String url) {
            this.url = url == null ? "" : url;
            return (T) this;
        }

        public T tag(Object tag) {
            this.tag = tag;
            return (T) this;
        }

        public T addHeader(String name, String value) {
            if (headers == null) {
                headers = CollectionUtil.linkedHashMap();
            }
            headers.put(name, value);
            return (T) this;
        }

        public T headers(Map<String, String> headers) {
            this.headers = headers;
            return (T) this;
        }

        public T params(Map<String, String> params) {
            this.params = params;
            return (T) this;
        }

        public abstract <T extends OKHttpRequest> T build();
    }

    public String getUrl() {
        return url;
    }

    public Object getTag() {
        return tag;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void cancel(Object tag) {
        okHttpManager.cancel(tag);
    }

    public void request(ResponseHandlerInterface responseHandlerInterface) throws IOException {
        Call call = okHttpManager.getOkHttpClient().newCall(buildRequest());
        okHttpManager.sendRequest(call, responseHandlerInterface);
    }

    public Response request() throws IOException {
        Call call = okHttpManager.getOkHttpClient().newCall(buildRequest());
        return okHttpManager.sendRequest(call);
    }


    protected Request.Builder getBuilder() {
        Request.Builder builder = new Request.Builder();
        Object tag = getTag();

        if (tag != null) {
            builder.tag(tag);
        }

//		builder.header("","");

        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> param : headers.entrySet()) {
                builder.addHeader(param.getKey(), param.getValue());
            }
        }
        return builder;
    }

    public abstract Request buildRequest();

    protected String appendUrl(String url, Map<String, String> urlParams) {
        if (urlParams == null || urlParams.size() <= 0) {
            return url;
        }
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        for (Map.Entry<String, String> entry : urlParams.entrySet()) {
            urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        return urlBuilder.build().toString();
    }
}
