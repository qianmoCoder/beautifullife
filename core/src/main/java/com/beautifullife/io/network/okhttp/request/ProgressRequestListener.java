package com.beautifullife.io.network.okhttp.request;

public interface ProgressRequestListener {

	void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}
