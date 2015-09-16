package com.dream.beautifullife.network;

import java.io.IOException;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.ResponseBody;

import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 创建日期：2015年9月6日 版权所有 悦畅科技有限公司。 保留所有权利。<br>
 * 项目名：悟空停车 - Android客户端<br>
 * 描述：
 * 
 * @author admin
 */
public class ProgressResponseBody extends ResponseBody {

	private final ResponseBody responseBody;

	private ProgressHttpResponseHandler progressHttpResponseHandler;

	private BufferedSource bufferedSource;

	public ProgressResponseBody(ResponseBody responseBody) {
		this.responseBody = responseBody;
	}

	public void setListener(ProgressHttpResponseHandler progressHttpResponseHandler) {
		this.progressHttpResponseHandler = progressHttpResponseHandler;
	}

	@Override
	public long contentLength() throws IOException {
		return responseBody.contentLength();
	}

	@Override
	public MediaType contentType() {
		return responseBody.contentType();
	}

	@Override
	public BufferedSource source() throws IOException {
		if (bufferedSource == null) {
			bufferedSource = Okio.buffer(source(responseBody.source()));
		}
		return bufferedSource;
	}

	private Source source(Source source) {
		return new ForwardingSource(source) {

			long totalBytesRead = 0L;

			@Override
			public long read(Buffer sink, long byteCount) throws IOException {
				long bytesRead = super.read(sink, byteCount);
				totalBytesRead += bytesRead != -1 ? bytesRead : 0;
				if (progressHttpResponseHandler != null) {
					progressHttpResponseHandler.onProgress(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
				}
				return bytesRead;
			}
		};
	}
}
