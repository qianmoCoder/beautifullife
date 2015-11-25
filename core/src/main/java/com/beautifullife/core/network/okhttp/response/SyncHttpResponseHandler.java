package com.beautifullife.core.network.okhttp.response;


import android.util.Pair;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public abstract class SyncHttpResponseHandler implements ResponseHandlerInterface {

    public final void onResponse(final Response response) {
        if (response.isSuccessful()) {
            try {
                String string = response.body().string();
                Observable.just(string).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        onUIResponse(s);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                Observable.just(e).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Exception>() {
                    @Override
                    public void call(Exception e) {
                        onUIError(e);
                    }
                });
            }
        } else {
            Observable.just(response).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Response>() {
                @Override
                public void call(Response response) {
                    onUIFailed(response.code(), response);
                }
            });
        }
    }

    public final void onFailure(Request request, Exception e) {
        Observable.just(Pair.create(request, e)).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Pair<Request, Exception>>() {

            @Override
            public void call(Pair<Request, Exception> requestExceptionPair) {
                onUIFailure(requestExceptionPair.first, requestExceptionPair.second);
            }
        });
    }

    public abstract void onUIResponse(String string);

    public abstract void onUIFailure(Request request, Exception e);

    public abstract void onUIFailed(int errorCode, Response response);

    public abstract void onUIError(Throwable e);
}
