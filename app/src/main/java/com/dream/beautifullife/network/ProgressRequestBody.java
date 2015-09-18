package com.dream.beautifullife.network;

import java.io.IOException;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class ProgressRequestBody extends RequestBody {

	private final RequestBody requestBody;

	private BufferedSink bufferedSink;
	private ProgressHttpResponseHandler progressHttpResponseHandler;

	public ProgressRequestBody(RequestBody requestBody) {
		this.requestBody = requestBody;
	}

	public void setListener(ProgressHttpResponseHandler progressHttpResponseHandler) {
		this.progressHttpResponseHandler = progressHttpResponseHandler;
	}

	@Override
	public long contentLength() throws IOException {
		return requestBody.contentLength();
	}

	@Override
	public MediaType contentType() {
		return requestBody.contentType();
	}

	@Override
	public void writeTo(BufferedSink paramBufferedSink) throws IOException {
		if (bufferedSink == null) {
			bufferedSink = Okio.buffer(sink(paramBufferedSink));
		}
		requestBody.writeTo(bufferedSink);
		bufferedSink.flush();
	}

	private Sink sink(Sink sink) {
		return new ForwardingSink(sink) {

			long bytesWritten = 0L;

			long contentLength = 0L;

			@Override
			public void write(Buffer source, long byteCount) throws IOException {
				super.write(source, byteCount);
				if (contentLength == 0) {
					contentLength = contentLength();
				}
				bytesWritten += byteCount;
				if (progressHttpResponseHandler != null) {
					progressHttpResponseHandler.onUIProgress(bytesWritten, contentLength);
				}
			}
		};
	}
}
