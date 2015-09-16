package com.dream.beautifullife.network;

public abstract class ProgressHttpResponseHandler extends StreamHttpResponseHandler {

	public abstract void onProgress(long bytesWritten, long contentLength, boolean done);

}
