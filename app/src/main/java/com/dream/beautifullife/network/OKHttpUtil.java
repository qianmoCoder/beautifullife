package com.dream.beautifullife.network;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class OKHttpUtil {

	private static OkHttpClient sOkHttpClient = new OkHttpClient();

	static {
		sOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
	}

	// �������첽�߳�
	public static Response execute(Request request) throws IOException {
		return sOkHttpClient.newCall(request).execute();
	}

	// �����첽�̷߳�������
	public static void enqueue(Request request, Callback responseCallback) {
		sOkHttpClient.newCall(request).enqueue(responseCallback);
	}

	public static String getStringFromServer(String url) throws IOException {
		Request request = new Request.Builder().url(url).build();
		Response response = execute(request);
		if (response.isSuccessful()) {
			return response.body().string();
		} else {
			throw new IOException("Unexpected code " + response);
		}
	}

	private static final String CHARSET_NAME = "UTF-8";

	public static String formatParams(List<BasicNameValuePair> params) {
		return URLEncodedUtils.format(params, CHARSET_NAME);
	}

	public static String attachHttpGetParams(String url, List<BasicNameValuePair> params) {
		return url + "?" + formatParams(params);
	}

	public static String attachHttpGetParam(String url, String name, String value) {
		return url + "?" + name + "=" + value;
	}

}
