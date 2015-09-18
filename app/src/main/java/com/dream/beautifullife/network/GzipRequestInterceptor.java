package com.dream.beautifullife.network;

import java.io.IOException;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;

public class GzipRequestInterceptor implements Interceptor {

	@Override
	public Response intercept(Chain paramChain) throws IOException {
		Request originalRequest = paramChain.request();
		if (originalRequest.body() == null || originalRequest.header("Content-Encoding") != null) {
			return paramChain.proceed(originalRequest);
		}

		Request compressedRequest = originalRequest.newBuilder().header("Content-Encoding", "gzip").method(originalRequest.method(), gzip(originalRequest.body())).build();
		return paramChain.proceed(compressedRequest);
	}

	private RequestBody gzip(final RequestBody body) {
		return new RequestBody() {

			@Override
			public void writeTo(BufferedSink paramBufferedSink) throws IOException {
				BufferedSink gzipSink = Okio.buffer(new GzipSink(paramBufferedSink));
				body.writeTo(gzipSink);
				gzipSink.close();
			}

			@Override
			public MediaType contentType() {
				return body.contentType();
			}

			@Override
			public long contentLength() throws IOException {
				return -1;// 无法知道压缩后的大小
			}
		};
	}

}
