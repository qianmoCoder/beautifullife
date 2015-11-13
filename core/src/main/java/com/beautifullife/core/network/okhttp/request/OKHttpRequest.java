package com.beautifullife.core.network.okhttp.request;

import com.beautifullife.core.network.okhttp.OKHttpManager;
import com.beautifullife.core.network.okhttp.response.ResponseHandlerInterface;

import java.io.IOException;
import java.util.Map;

public abstract class OKHttpRequest {

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";

    public static final String METHOD_OTHER = "";

    private final String url;
    private final Object tag;
    private final Map<String, String> headers;
    private final Map<String, String> params;
    protected String method;

    protected OKHttpManager okHttpManager;

    protected OKHttpRequest(Builder builder) {
        this.url = builder.url;
        this.tag = builder.tag;
        this.headers = builder.headers;
        this.params = builder.params;
        this.method = METHOD_OTHER;
        okHttpManager = OKHttpManager.getInstance();
    }

    public abstract static class Builder {

        private String url;
        private Object tag;
        private Map<String, String> headers;
        private Map<String, String> params;

        public Builder url(String url) {
            this.url = url == null ? "" : url;
            return this;
        }

        public Builder tag(Object tag) {
            this.tag = tag;
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder params(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public abstract <T> T build();
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

    public String getMethod() {
        return method;
    }

    public void request(ResponseHandlerInterface responseHandlerInterface) throws IOException {
        okHttpManager.request(this, responseHandlerInterface);
    }

    public void cancel(Object tag) {
        okHttpManager.cancel(tag);
    }

}
