package com.dream.beautifullife.network;

import android.os.Message;

import com.squareup.okhttp.Response;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public abstract class ProgressHttpResponseHandler extends SyncHttpResponseHandler {

	private File mFile;

	public ProgressHttpResponseHandler() {
	}

	public ProgressHttpResponseHandler(File downFile) {
		this.mFile = downFile;
	}

	@Override
	public void onResponse(Response response) {
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		try {
			InputStream inputStream = response.body().byteStream();
			bufferedInputStream = new BufferedInputStream(inputStream);

			long length = response.body().contentLength();
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
				Message msg = Message.obtain();
				msg.obj = new AsyncTaskResult(bytesWritten, length);
				msg.what = ONUIPROGRESS;
				mHandler.sendMessage(msg);
			}
			if (bufferedOutputStream != null) {
				bufferedOutputStream.flush();
			}

		} catch (Exception e) {
			e.printStackTrace();
			onUIError(response.code(), response, e);
		} finally {
			try {
				if (bufferedInputStream != null) {
					bufferedInputStream.close();
				}

				if (bufferedOutputStream != null) {
					bufferedOutputStream.close();
				}
			} catch (Exception e) {
				onUIError(response.code(), response, e);
			}
		}
	}
}
