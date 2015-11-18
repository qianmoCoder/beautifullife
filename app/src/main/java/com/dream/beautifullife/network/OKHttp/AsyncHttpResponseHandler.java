package com.dream.beautifullife.network.OKHttp;

import com.dream.beautifullife.network.OKHttp.ResponseHandlerInterface;
import com.squareup.okhttp.Response;

public abstract class AsyncHttpResponseHandler implements ResponseHandlerInterface {

	public abstract void onError(int code, Response response, Throwable e);

}
