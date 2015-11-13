package com.beautifullife.core.network.okhttp.request;

/**
 * Created by admin on 2015/11/13.
 */
public class OKHttpPostRequest extends OKHttpRequest {

    private OKHttpPostRequest(Builder builder) {
        super(builder);
        this.method = METHOD_POST;
    }

    public static class Builder extends OKHttpRequest.Builder {

        @Override
        public OKHttpPostRequest build() {
            return new OKHttpPostRequest(this);
        }
    }

    public static Builder create() {
        return new Builder();
    }
}
