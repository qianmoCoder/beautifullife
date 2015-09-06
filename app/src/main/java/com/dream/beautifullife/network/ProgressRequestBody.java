package com.dream.beautifullife.network;

import java.io.IOException;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * 创建日期：2015年9月6日 版权所有 悦畅科技有限公司。 保留所有权利。<br>
 * 项目名：悟空停车 - Android客户端<br>
 * 描述：
 * 
 * @author admin
 */
public class ProgressRequestBody extends RequestBody {

	private final RequestBody requestBody;

	private final ProgressResponseListener progressListener;

	private BufferedSink bufferedSink;

	public ProgressRequestBody(RequestBody requestBody, ProgressResponseListener progressListener) {
		this.requestBody = requestBody;
		this.progressListener = progressListener;
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
				progressListener.onResponseProgress(bytesWritten, contentLength, bytesWritten == contentLength);
			}
		};
	}
}
