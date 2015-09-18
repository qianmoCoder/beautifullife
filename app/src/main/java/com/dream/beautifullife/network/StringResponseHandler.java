package com.dream.beautifullife.network;

import com.squareup.okhttp.Response;

import java.io.IOException;

public abstract class StringResponseHandler extends SyncHttpResponseHandler {

	@Override
	public void onResponse(Response response) {
		if (response.isSuccessful()) {
			try {
				String string = response.body().string();
				onResponse(string);
			} catch (IOException e) {
				onUIError(response.code(), response, e);
			}
		} else {
			onUIError(response.code(), response, new Throwable());
		}
	}

	public abstract void onResponse(String response);

}
