package com.dream.beautifullife.network;

import java.lang.ref.WeakReference;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public abstract class SyncHttpResponseHandler implements ResponseHandlerInterface {

	private static final int ONRESPONSE = 0x001;

	private static final int ONFAILURE = 0x002;

	private static final int ONERROR = 0x003;

	protected static final int ONUIPROGRESS = 0x004;

	protected final Handler mHandler = new UIHandler(Looper.getMainLooper(), this);

	public void onResponse(Response response) {
		Message msg = Message.obtain();
		msg.obj = new AsyncTaskResult(response);
		if (response.isSuccessful()) {
			msg.what = ONRESPONSE;
		} else {
			msg.what = ONERROR;
		}
		mHandler.sendMessage(msg);
	}

	public void onFailure(Request request, Exception e) {
		Message msg = Message.obtain();
		msg.what = ONFAILURE;
		msg.obj = new AsyncTaskResult(request, e);
		mHandler.sendMessage(msg);
	}

	public abstract void onUIResponse(Response response);

	public abstract void onUIFailure(Request request, Exception e);

	public abstract void onUIError(int code, Response response, Throwable e);

	public abstract void onUIProgress(long bytes, long contentLength);

	private static class UIHandler extends Handler {
		// 弱引用
		private final WeakReference<SyncHttpResponseHandler> mSyncHttpResponseHandler;

		public UIHandler(Looper looper, SyncHttpResponseHandler uiProgressResponseListener) {
			super(looper);
			mSyncHttpResponseHandler = new WeakReference<SyncHttpResponseHandler>(uiProgressResponseListener);
		}

		@Override
		public void handleMessage(Message msg) {
			SyncHttpResponseHandler uiProgressResponseListener = mSyncHttpResponseHandler.get();
			switch (msg.what) {
				case ONRESPONSE:
					uiProgressResponseListener.onUIResponse((Response) msg.obj);
					break;
				case ONFAILURE: {
					AsyncTaskResult result = (AsyncTaskResult) msg.obj;
					uiProgressResponseListener.onUIFailure((Request) result.mData[0], (Exception) result.mData[1]);
					break;
				}
				case ONERROR:
					Response response = (Response) msg.obj;
					uiProgressResponseListener.onUIError(response.code(), response, new Throwable());
					break;
				case ONUIPROGRESS: {
					AsyncTaskResult result = (AsyncTaskResult) msg.obj;
					uiProgressResponseListener.onUIProgress((Long) result.mData[0], (Long) result.mData[1]);
				}
					break;
				default:
					break;
			}
		}
	}

	protected static class AsyncTaskResult {
		final Object[] mData;

		AsyncTaskResult(Object... data) {
			mData = data;
		}
	}

}
