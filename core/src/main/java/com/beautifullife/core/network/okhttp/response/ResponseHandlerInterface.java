package com.beautifullife.core.network.okhttp.response;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public interface ResponseHandlerInterface {

    void onResponse(Response response);

    void onFailure(Request request, Exception e);
}
