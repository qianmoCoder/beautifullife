package com.beautifullife.io.network.okhttp.request;

import java.util.Map;

public class OKHttpRequest {

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";

    private final String url;
    private final Object tag;
    private final Map<String, String> headers;
    private final Map<String, String> params;

    private final String method;

    private OKHttpRequest(Builder builder) {
        this.url = builder.url;
        this.tag = builder.tag;
        this.headers = builder.headers;
        this.params = builder.params;
        this.method = builder.method;
    }

    public static class Builder {

        private String url;
        private Object tag;
        private Map<String, String> headers;
        private Map<String, String> params;

        private String method;

        public Builder get() {
            this.method = METHOD_GET;
            return this;
        }

        public Builder post() {
            this.method = METHOD_POST;
            return this;
        }

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

        public OKHttpRequest build() {
            return new OKHttpRequest(this);
        }
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

}
