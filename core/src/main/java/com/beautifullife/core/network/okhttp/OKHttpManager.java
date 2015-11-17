package com.beautifullife.core.network.okhttp;

import com.beautifullife.core.network.okhttp.response.ResponseHandlerInterface;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * @author admin
 */
public class OKHttpManager {

    private OkHttpClient mOkHttpClient;

    private OKHttpManager() {
        mOkHttpClient = new OkHttpClient();
    }

    public static OKHttpManager getInstance() {
        return SingletonHolder.instance;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    private static class SingletonHolder {
        private static OKHttpManager instance = new OKHttpManager();
    }

    public void cancel(Object tag) {
        mOkHttpClient.cancel(tag);
    }

    public Response sendRequest(Call call) throws IOException {
        return call.execute();
    }

    public void sendRequest(Call call, ResponseHandlerInterface responseHandlerInterface) {
        deliveryResult(call, responseHandlerInterface);
    }

    private void deliveryResult(final Call call, final ResponseHandlerInterface responseHandlerInterface) {
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Response response) throws IOException {
                responseHandlerInterface.onResponse(response);
            }

            @Override
            public void onFailure(Request paramRequest, IOException paramIOException) {
                responseHandlerInterface.onFailure(paramRequest, paramIOException);
            }
        });
    }
}
