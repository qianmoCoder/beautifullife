package com.beautifullife.core.network.okhttp.response;


import android.util.Pair;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public abstract class SyncHttpResponseHandler implements ResponseHandlerInterface {

    public final void onResponse(Response response) {
        Observable.just(response).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Response>() {
            @Override
            public void call(Response response) {
                if (response.isSuccessful()) {
                    onUIResponse(response);
                } else {
                    onUIFailed(response.code(), response);
                }
            }
        });
    }

    public final void onFailure(Request request, Exception e) {
        Observable.just(Pair.create(request, e)).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Pair<Request, Exception>>() {

            @Override
            public void call(Pair<Request, Exception> requestExceptionPair) {
                onUIFailure(requestExceptionPair.first, requestExceptionPair.second);
            }
        });
    }

    public abstract void onUIResponse(Response response);

    public abstract void onUIFailure(Request request, Exception e);

    public abstract void onUIFailed(int errorCode, Response response);

    public abstract void onUIError(Throwable e);
}
