package com.beautifullife.core.network.okhttp.response;

import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;

public abstract class StreamHttpResponseHandler extends AsyncHttpResponseHandler {

    @Override
    public void onResponsed(Response response) {
        try {
            onResponse(response.body().byteStream());
        } catch (IOException e) {
            onError(e);
        }
    }

    public abstract void onResponse(InputStream response);

}
