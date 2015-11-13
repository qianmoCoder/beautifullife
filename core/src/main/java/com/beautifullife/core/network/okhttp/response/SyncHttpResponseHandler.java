package com.beautifullife.core.network.okhttp.response;


import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public abstract class SyncHttpResponseHandler implements ResponseHandlerInterface {

    public final void onResponse(Response response) {
        Observable.just(response).observeOn(Schedulers.computation()).subscribe(new Action1<Response>() {
            @Override
            public void call(Response response) {
                if (response.isSuccessful()) {
                    onUIResponse(response);
                } else {
                    onUIError(response.code(), response, new Throwable());
                }
            }
        });
    }

    public final void onFailure(Request request, final Exception e) {
        Observable.just(request).observeOn(Schedulers.computation()).subscribe(new Action1<Request>() {
            @Override
            public void call(Request request) {
                onUIFailure(request, e);
            }
        });
    }

    public abstract void onUIResponse(Response response);

    public abstract void onUIFailure(Request request, Exception e);

    public abstract void onUIError(int code, Response response, Throwable e);

}
