package com.beautifullife.core.network.okhttp.request;

import com.beautifullife.core.network.okhttp.response.ResponseHandlerInterface;

import java.io.IOException;

/**
 * Created by admin on 2015/11/13.
 */
public class OKHttpDownRequest extends OKHttpRequest {

    private OKHttpDownRequest(Builder builder) {
        super(builder);
        this.method = METHOD_GET;
    }

    public static class Builder extends OKHttpRequest.Builder {

        @Override
        public OKHttpDownRequest build() {
            return new OKHttpDownRequest(this);
        }
    }

    public static Builder create() {
        return new Builder();
    }

    @Override
    public void request(ResponseHandlerInterface responseHandlerInterface) throws IOException {


    }
}
