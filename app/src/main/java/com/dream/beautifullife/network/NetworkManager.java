package com.dream.beautifullife.network;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.dream.beautifullife.util.MD5Utils;
import com.dream.beautifullife.util.URLUtil;
import com.squareup.okhttp.Request;

import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author admin
 */
public class NetworkManager {

	public static void get(Context context, String url, HashMap<String, String> params, String md5Param) throws Exception {
		PackageManager manager = context.getPackageManager();
		PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("versionName", info.versionName);
		headers.put("platform", "android");

		HashMap<String, String> tempParams = URLUtil.encodeValueUTF8(params);

		if (!TextUtils.isEmpty(md5Param)) {
			if (params != null) {
				String validate = MD5Utils.validate(tempParams);
				tempParams.put(md5Param, URLEncoder.encode(validate, "UTF-8"));
			}
		}

		OKHttpRequest httpRequest = OKHttpRequest.getInstanceByGet(url);
		httpRequest.setHeaders(headers);
		httpRequest.setParams(tempParams);

		OKHttpManager.getInstance().request(httpRequest, new OKHttpManager.ResultCallBack<String>() {

			@Override
			public void onSuccess(String response) {
			}

			@Override
			public void onFailure(Request request, Exception e) {

			}

		});
	}

	public static void post(String url, HashMap<String, String> params) {

	}
}
