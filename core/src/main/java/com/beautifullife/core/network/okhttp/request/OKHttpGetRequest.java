package com.beautifullife.core.network.okhttp.request;

/**
 * Created by admin on 2015/11/13.
 */
public class OKHttpGetRequest extends OKHttpRequest {

    private OKHttpGetRequest(Builder builder) {
        super(builder);
        this.method = METHOD_GET;
    }

    public static class Builder extends OKHttpRequest.Builder {

        @Override
        public OKHttpGetRequest build() {
            return new OKHttpGetRequest(this);
        }
    }

    public static Builder create() {
        return new Builder();
    }

}
