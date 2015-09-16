package com.dream.beautifullife.network;

import com.squareup.okhttp.Response;

import java.io.IOException;

public abstract class StringResponseHandler extends AsyncHttpResponseHandler {


	@Override
	public void onResponse(Response response) {
		if (response.isSuccessful()) {
			try {
				String string = response.body().string();
				onResponse(string);
			} catch (IOException e) {
				onError(response.code(), response);
			}
		} else {
			onError(response.code(), response);
		}
	}

	public abstract void onResponse(String response);

}
