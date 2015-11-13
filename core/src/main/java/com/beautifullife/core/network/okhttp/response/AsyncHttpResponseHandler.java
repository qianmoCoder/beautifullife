package com.beautifullife.core.network.okhttp.response;

import com.squareup.okhttp.Response;

public abstract class AsyncHttpResponseHandler implements ResponseHandlerInterface {

    public abstract void onError(int code, Response response, Throwable e);

}
