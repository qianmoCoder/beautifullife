package com.beautifullife.core.network.okhttp.response;

import com.squareup.okhttp.Response;

import java.io.IOException;

public abstract class StringResponseHandler extends SyncHttpResponseHandler {

    @Override
    public void onUIResponse(Response response) {
        try {
            String string = response.body().string();
            onUIResponse(string);
        } catch (IOException e) {
            onUIError(e);
        }
    }

    public abstract void onUIResponse(String response);

}
