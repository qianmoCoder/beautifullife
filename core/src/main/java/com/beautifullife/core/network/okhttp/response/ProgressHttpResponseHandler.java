package com.beautifullife.core.network.okhttp.response;

import com.squareup.okhttp.Response;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public abstract class ProgressHttpResponseHandler extends AsyncHttpResponseHandler {

    private File mFile;

    public ProgressHttpResponseHandler() {
    }

    public ProgressHttpResponseHandler(File downFile) {
        this.mFile = downFile;
    }

    public void setFile(File file) {
        this.mFile = file;
    }

    public File getFile() {
        return mFile;
    }

    @Override
    public void onResponsed(final Response response) {
        Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                BufferedInputStream bufferedInputStream = null;
                BufferedOutputStream bufferedOutputStream = null;
                try {
                    InputStream inputStream = response.body().byteStream();
                    bufferedInputStream = new BufferedInputStream(inputStream);

                    if (mFile != null) {
                        bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File("")));
                    }
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    long bytesWritten = 0L;
                    while ((len = bufferedInputStream.read(buffer)) != -1) {
                        if (bufferedOutputStream != null) {
                            bufferedOutputStream.write(buffer, 0, len);
                        }
                        bytesWritten += len;
                        subscriber.onNext(bytesWritten);
                    }
                    if (bufferedOutputStream != null) {
                        bufferedOutputStream.flush();
                    }
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                } finally {
                    try {
                        if (bufferedInputStream != null) {
                            bufferedInputStream.close();
                        }

                        if (bufferedOutputStream != null) {
                            bufferedOutputStream.close();
                        }
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Long>() {

            @Override
            public void onCompleted() {
                try {
                    long length = response.body().contentLength();
                    onUIProgress(length, length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable paramThrowable) {
                onUIError(paramThrowable);
            }

            @Override
            public void onNext(Long paramT) {
                try {
                    long length = response.body().contentLength();
                    onUIProgress(paramT, length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onFailed(int errorCode, Response response) {
        Observable.just(response).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Response>() {
            @Override
            public void call(Response response) {
                onUIFailed(response.code(), response);
            }
        });
    }

    public abstract void onUIProgress(long bytes, long contentLength);

    public abstract void onUIError(Throwable e);

    public abstract void onUIFailed(int errorCode, Response response);
}
