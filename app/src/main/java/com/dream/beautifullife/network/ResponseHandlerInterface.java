package com.dream.beautifullife.network;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public interface ResponseHandlerInterface {

	public void onResponse(Response response);

	public void onFailure(Request request, Exception e);
}
