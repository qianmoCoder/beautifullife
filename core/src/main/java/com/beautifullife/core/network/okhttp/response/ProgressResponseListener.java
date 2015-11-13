package com.beautifullife.core.network.okhttp.response;

public interface ProgressResponseListener {

    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
