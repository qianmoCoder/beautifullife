package com.dream.beautifullife.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * 创建日期：2015年9月6日 版权所有 悦畅科技有限公司。 保留所有权利。<br>
 * 项目名：悟空停车 - Android客户端<br>
 * 描述：
 * 
 * @author admin
 */
public class OKHttpManager {

	private OkHttpClient mOkHttpClient;

	private OKHttpManager() {
		mOkHttpClient = new OkHttpClient();
	}

	public static OKHttpManager getInstance() {
		return SingletonHolder.instance;
	}

	private static class SingletonHolder {
		private static OKHttpManager instance = new OKHttpManager();
	}

	private Request.Builder getRequestBuilder(String url, Object tag) {
		Request.Builder builder = new Request.Builder().url(url);
		if (tag != null) {
			builder.tag(tag);
		}
		return builder;
	}

	private Request buildGetRequest(String url, Object tag) {
		Request.Builder builder = getRequestBuilder(url, tag);
		return builder.build();
	}

	private Request buildPostRequest(String url, List<Param<String, String>> params, Object tag) {
		if (params == null) {
			params = new ArrayList<Param<String, String>>();
		}
		FormEncodingBuilder builder = new FormEncodingBuilder();
		for (Param<String, String> param : params) {
			builder.add(param.first, param.second);
		}

		RequestBody requestBody = builder.build();

		Request.Builder reqBuilder = getRequestBuilder(url, tag);
		reqBuilder.post(requestBody);

		return reqBuilder.build();
	}

	public Response get(Request request) throws IOException {
		Call call = mOkHttpClient.newCall(request);
		Response response = call.execute();
		return response;
	}

	public Response get(String url, Object tag) throws IOException {
		Request request = buildGetRequest(url, tag);
		return get(request);
	}

	public Response get(String url) throws IOException {
		return get(url, null);
	}

	public String getAsString(String url, Object tag) throws IOException {
		Response response = get(url, tag);
		return response.body().string();
	}

	public String getAsString(String url) throws IOException {
		return getAsString(url, null);
	}

	public void getAsync(Request request) {
		deliveryResult(request);
	}

	public void getAsync(String url, String tag) {
		Request request = buildGetRequest(url, tag);
		getAsync(request);
	}

	public void getAsync(String url) {
		getAsync(url, null);
	}

	private void deliveryResult(Request request) {
		Call call = mOkHttpClient.newCall(request);
		call.enqueue(new Callback() {

			@Override
			public void onResponse(Response response) throws IOException {

			}

			@Override
			public void onFailure(Request request, IOException e) {

			}
		});
	}

	public static class Param<K, V> {

		private K first;
		private V second;

		public Param() {
		}

		public Param(K first, V second) {
			this.first = first;
			this.second = second;
		}

		public String toString() {
			return "(" + first + "." + second + ")";
		}
	}

}
