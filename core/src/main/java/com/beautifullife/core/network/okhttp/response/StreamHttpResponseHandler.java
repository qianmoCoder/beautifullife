package com.beautifullife.core.network.okhttp.response;

import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;

public abstract class StreamHttpResponseHandler extends AsyncHttpResponseHandler {

	@Override
	public void onResponse(Response response) {
		try {
			onResponse(response.body().byteStream());
		} catch (IOException e) {
			onError(response.code(), response, e);
		}
	}

	public abstract void onResponse(InputStream response);

}
