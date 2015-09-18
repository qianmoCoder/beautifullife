package com.dream.beautifullife.network;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

/**
 * @author admin
 */
public class NetworkManager {

	private OKHttpManager mOkHttpManager;
	private static NetworkManager mNetworkManager;

	private Handler mHandler;

	private NetworkManager() {
		mOkHttpManager = OKHttpManager.getInstance();
		mHandler = new Handler(Looper.getMainLooper());
	}

	public static NetworkManager getInstance() {
		if (mNetworkManager == null) {
			mNetworkManager = new NetworkManager();
		}
		return mNetworkManager;
	}

	public static void get(Context context, String url, ResponseHandlerInterface responseHandlerInterface) throws Exception {
		get(context, url, null, "", true, responseHandlerInterface);
	}

	public static void get(Context context, String url, HashMap<String, String> params, boolean isEncode, ResponseHandlerInterface responseHandlerInterface) throws Exception {
		get(context, url, params, "", isEncode, responseHandlerInterface);
	}

	public static void get(Context context, String url, HashMap<String, String> params, String md5Param, boolean isEncode, ResponseHandlerInterface responseHandlerInterface) throws Exception {
		getInstance().request(context, url, params, md5Param, isEncode, new CallBack() {

			@Override
			public OKHttpRequest getRequest(String url) {
				return OKHttpRequest.getInstanceByGet(url);
			}
		}, responseHandlerInterface);
	}

	public static void post(Context context, String url, HashMap<String, String> params, boolean isEncode, ResponseHandlerInterface responseHandlerInterface) throws Exception {
		post(context, url, params, "", isEncode, responseHandlerInterface);
	}

	public static void post(Context context, String url, HashMap<String, String> params, String md5Param, boolean isEncode, ResponseHandlerInterface responseHandlerInterface) throws Exception {
		getInstance().request(context, url, params, md5Param, isEncode, new CallBack() {

			@Override
			public OKHttpRequest getRequest(String url) {
				return OKHttpRequest.getInstanceByPost(url);
			}
		}, responseHandlerInterface);
	}

	public static void request(OKHttpRequest request) throws IOException {
		getInstance().getOkHttpManager().request(request);
	}

	public void download(Context context, String url, HashMap<String, String> params, String md5Param, boolean isEncode, final ProgressHttpResponseHandler responseHandlerInterface) throws Exception {
		OKHttpRequest httpRequest = getRequest(context, url, params, md5Param, isEncode, new CallBack() {
			@Override
			public OKHttpRequest getRequest(String url) {
				return OKHttpRequest.getInstanceByGet(url);
			}
		});
		if (responseHandlerInterface instanceof ProgressHttpResponseHandler) {
			mOkHttpManager.request(httpRequest, responseHandlerInterface, OKHttpManager.METHOD_REPONSE);
		}
	}

	public void upload(Context context, String url, HashMap<String, String> params, String md5Param, boolean isEncode, CallBack callback, ResponseHandlerInterface responseHandlerInterface)
			throws Exception {
		OKHttpRequest httpRequest = getRequest(context, url, params, md5Param, isEncode, callback);
		if (responseHandlerInterface instanceof ProgressHttpResponseHandler) {
			mOkHttpManager.request(httpRequest, (ProgressHttpResponseHandler) responseHandlerInterface, OKHttpManager.METHOD_REQUEST);
		}
	}

	public void request(Context context, String url, HashMap<String, String> params, String md5Param, boolean isEncode, CallBack callback, ResponseHandlerInterface responseHandlerInterface)
			throws Exception {
		OKHttpRequest httpRequest = getRequest(context, url, params, md5Param, isEncode, callback);
		mOkHttpManager.request(httpRequest, responseHandlerInterface);
	}

	private OKHttpRequest getRequest(Context context, String url, HashMap<String, String> params, String md5Param, boolean isEncode, CallBack callback) throws Exception {
		PackageManager manager = context.getPackageManager();
		PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("versionName", info.versionName);
		headers.put("platform", "android");

		HashMap<String, String> tempParams;

		if (params == null) {
			tempParams = new HashMap<String, String>();
		} else {
			if (isEncode) {
				tempParams = URLUtil.encodeValueUTF8(params);
			} else {
				tempParams = (HashMap<String, String>) params.clone();
			}
		}

		if (!TextUtils.isEmpty(md5Param)) {
			if (params != null) {
				String validate = MD5Utils.validate(tempParams);
				tempParams.put(md5Param, URLEncoder.encode(validate, "UTF-8"));
			}
		}
		OKHttpRequest httpRequest = callback.getRequest(url);
		if (headers != null) {
			httpRequest.setHeaders(headers);
		}
		if (tempParams != null) {
			httpRequest.setParams(tempParams);
		}
		return httpRequest;
	}

	public OKHttpManager getOkHttpManager() {
		return mOkHttpManager;
	}

	public void cancel(Object tag) {
		mOkHttpManager.cancel(tag);
	}

	private interface CallBack {

		public OKHttpRequest getRequest(String url);
	}

}
