package com.beautifullife.core.network.okhttp.request;

public interface ProgressRequestListener {

    void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}
