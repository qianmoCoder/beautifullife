package com.beautifullife.core.network.okhttp.response;

import com.squareup.okhttp.Response;

public abstract class AsyncHttpResponseHandler implements ResponseHandlerInterface {

    @Override
    public final void onResponse(Response response) {
        if (response.isSuccessful()) {
            onResponsed(response);
        } else {
            onFailed(response.code(), response);
        }

    }

    public abstract void onResponsed(Response response);

    public abstract void onFailed(int errorCode, Response response);

    public abstract void onError(Throwable e);

}
