package com.dream.beautifullife.network;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import okio.BufferedSink;

/**
 * @author admin
 */
public class OKHttpManager {

	public static final MediaType MEDIA_TYPE_APP_JSON = MediaType.parse("application/json;charset=utf-8");
	public static final MediaType MEDIA_TYPE_APP_OCTET_STREAM = MediaType.parse("application/octet-stream;charset=utf-8");

	public static final MediaType MEDIA_TYPE_TXT_PLAIN = MediaType.parse("text/plain;charset=utf-8");
	public static final MediaType MEDIA_TYPE_TXT_X_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");

	public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

	public static final int METHOD_REQUEST = 0x001;
	public static final int METHOD_REPONSE = 0x002;

	private OkHttpClient mOkHttpClient;

	private Handler mHandler;

	private OKHttpManager() {
		mOkHttpClient = new OkHttpClient();
		mHandler = new Handler(Looper.getMainLooper());
	}

	public static OKHttpManager getInstance() {
		return SingletonHolder.instance;
	}

	public OkHttpClient getOkHttpClient() {
		return mOkHttpClient;
	}

	private static class SingletonHolder {
		private static OKHttpManager instance = new OKHttpManager();
	}

	private void sendResponseMessage(final Response response, final ResponseHandlerInterface responseHandlerInterface) {
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				responseHandlerInterface.onResponse(response);
			}
		});
	}

	private <T> void sendFailureMessage(final Request request, final Exception e, final ResponseHandlerInterface responseHandlerInterface) {
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				responseHandlerInterface.onFailure(request, e);
			}
		});
	}

	private Builder createBuilder(OKHttpRequest httpRequest) throws IOException {

		Builder builder = new Builder();

		String url = httpRequest.getUrl();
		String method = httpRequest.getMethod();
		Object tag = httpRequest.getTag();

		Map<String, String> headers = httpRequest.getHeaders();
		Map<String, String> params = httpRequest.getParams();

		if (params != null && params.size() > 0) {
			Map<String, String> tempParams = URLUtil.encodeAllUTF8((HashMap<String, String>) params);
			if (method.equalsIgnoreCase(OKHttpRequest.METHOD_GET)) {
				String result = getParamString(tempParams);
				if (!url.contains("?")) {
					url = url + "?" + result;
				} else {
					url = url + "&" + result;
				}
			} else {
				List<Param<String, String>> res = mapToParams(params);
				FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
				for (Param<String, String> param : res) {
					formEncodingBuilder.add(param.first, param.second);
				}
				RequestBody requestBody = formEncodingBuilder.build();
				builder.post(requestBody);
			}
		}

		builder.url(url);

		if (tag != null) {
			builder.tag(tag);
		}

		if (headers != null) {
			for (Map.Entry<String, String> param : headers.entrySet()) {
				builder.addHeader(param.getKey(), param.getValue());
			}
		}

		return builder;
	}

	private Request buildPostRequest(OKHttpRequest httpRequest, RequestBody requestBody) throws IOException {
		Builder builder = createBuilder(httpRequest);
		builder.post(requestBody);
		return builder.build();
	}

	private Call buildCall(Request request) {
		Call call = mOkHttpClient.newCall(request);
		return call;
	}

	private List<Param<String, String>> mapToParams(Map<String, String> params) {
		List<Param<String, String>> res = new ArrayList<Param<String, String>>();
		if (params == null)
			return res;
		Set<Map.Entry<String, String>> entries = params.entrySet();
		for (Map.Entry<String, String> entry : entries) {
			res.add(new Param<String, String>(entry.getKey(), entry.getValue()));
		}
		return res;
	}

	private String getParamString(Map<String, String> urlParams) {
		StringBuilder result = new StringBuilder();
		for (Map.Entry<String, String> entry : urlParams.entrySet()) {
			if (result.length() > 0) {
				result.append("&");
			}
			result.append((String) entry.getKey());
			result.append("=");
			result.append((String) entry.getValue());
		}
		return result.toString();
	}

	/**
	 * sync
	 */
	public Response request(OKHttpRequest httpRequest) throws IOException {
		Request request = createBuilder(httpRequest).build();
		return sendRequest(request);
	}

	/**
	 * async
	 */
	public void request(OKHttpRequest httpRequest, ResponseHandlerInterface responseHandlerInterface, boolean isRunOnUiThread) throws IOException {
		Request request = createBuilder(httpRequest).build();
		sendRequest(request, responseHandlerInterface, isRunOnUiThread);
	}

	public void request(OKHttpRequest httpRequest, byte[] bodyBytes, ResponseHandlerInterface responseHandlerInterface, boolean isRunOnUiThread) throws IOException {
		RequestBody requestBody = RequestBody.create(MEDIA_TYPE_APP_OCTET_STREAM, bodyBytes);
		request(httpRequest, requestBody, responseHandlerInterface, isRunOnUiThread);
	}

	public void request(OKHttpRequest httpRequest, RequestBody requestBody, ResponseHandlerInterface responseHandlerInterface, boolean isRunOnUiThread) throws IOException {
		Request request = buildPostRequest(httpRequest, requestBody);
		sendRequest(request, responseHandlerInterface, isRunOnUiThread);
	}
	
	public void request(OKHttpRequest httpRequest, final ProgressHttpResponseHandler progressHttpResponseHandler, boolean isRunOnUiThread, int type) throws IOException {
		if (type == METHOD_REPONSE) {
			OkHttpClient client = mOkHttpClient.clone();
			client.networkInterceptors().add(new Interceptor() {
				@Override
				public Response intercept(Chain chain) throws IOException {
					// 拦截
					Response originalResponse = chain.proceed(chain.request());
					// 包装响应体并返回
					ProgressResponseBody responseBody = new ProgressResponseBody(originalResponse.body());
					responseBody.setListener(progressHttpResponseHandler);
					return originalResponse.newBuilder().body(responseBody).build();
				}
			});
			Request request = createBuilder(httpRequest).build();
			Call call = client.newCall(request);
			sendRequest(call, progressHttpResponseHandler, isRunOnUiThread);
		}

		if(type == METHOD_REQUEST){
			ProgressRequestBody requestBody = new ProgressRequestBody(getRequest().body());
			requestBody.setListener(progressHttpResponseHandler);
			Request request = buildPostRequest(httpRequest, requestBody);
			sendRequest(request, progressHttpResponseHandler, isRunOnUiThread);
		}
	}

	public void cancel(Object tag) {
		mOkHttpClient.cancel(tag);
	}

	private Response sendRequest(Request request) throws IOException {
		Call call = buildCall(request);
		return call.execute();
	}

	private void sendRequest(Request request, ResponseHandlerInterface responseHandlerInterface, boolean isRunOnUiThread) {
		Call call = buildCall(request);
		sendRequest(call, responseHandlerInterface, isRunOnUiThread);
	}

	private void sendRequest(Call call, ResponseHandlerInterface responseHandlerInterface, boolean isRunOnUiThread) {
		deliveryResult(call, responseHandlerInterface, isRunOnUiThread);
	}

	private void deliveryResult(final Call call, final ResponseHandlerInterface responseHandlerInterface, final boolean isRunOnUiThread) {
		call.enqueue(new Callback() {

			@Override
			public void onResponse(Response response) throws IOException {
				if (isRunOnUiThread) {
					sendResponseMessage(response, responseHandlerInterface);
				} else {
					responseHandlerInterface.onResponse(response);
				}
			}

			@Override
			public void onFailure(Request paramRequest, IOException paramIOException) {
				if (isRunOnUiThread) {
					sendFailureMessage(paramRequest, paramIOException, responseHandlerInterface);
				} else {
					responseHandlerInterface.onFailure(paramRequest, paramIOException);
				}
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

	/**
	 * 使用HTTP POST提交请求到服务。这个例子提交了一个markdown文档到web服务，以HTML方式渲染markdown。因为整个请求体都在内存中，因此避免使用此api提交大文档（大于1MB）
	 * 
	 * @param url
	 */
	public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");

	public void postString(String url) throws IOException {
		String postBody = "" + "Releases\n" + "--------\n" + "\n" + " * _1.0_ May 6, 2013\n" + " * _1.1_ June 15, 2013\n" + " * _1.2_ August 11, 2013\n";
		RequestBody formBody = RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody);
		Request request = new Builder().url(url).post(formBody).build();
		Response response = mOkHttpClient.newCall(request).execute();
		if (response.isSuccessful()) {
			Log.v("", response.body().string());
		} else {
			Log.v("", response.code() + "");
		}
	}

	// 以流的方式POST提交请求体。请求体的内容由流写入产生。这个例子是流直接写入Okio的BufferedSink。你的程序可能会使用OutputStream，你可以使用BufferedSink.outputStream()来获取。
	public static final MediaType MEDIA_TYPE_MARKDOWN_STREAM = MediaType.parse("text/x-markdown; charset=utf-8");

	public void postStream() throws Exception {
		RequestBody requestBody = new RequestBody() {
			@Override
			public MediaType contentType() {
				return MEDIA_TYPE_MARKDOWN;
			}

			@Override
			public void writeTo(BufferedSink sink) throws IOException {
				sink.writeUtf8("Numbers\n");
				sink.writeUtf8("-------\n");
				for (int i = 2; i <= 997; i++) {
					sink.writeUtf8(String.format(" * %s = %s\n", i, factor(i)));
				}
			}

			private String factor(int n) {
				for (int i = 2; i < n; i++) {
					int x = n / i;
					if (x * i == n)
						return factor(x) + " × " + i;
				}
				return Integer.toString(n);
			}
		};

		Request request = new Builder().url("https://api.github.com/markdown/raw").post(requestBody).build();

		Response response = mOkHttpClient.newCall(request).execute();
		if (!response.isSuccessful())
			throw new IOException("Unexpected code " + response);

		System.out.println(response.body().string());
	}

	// 使用FormEncodingBuilder来构建和HTML<form>标签相同效果的请求体。键值对将使用一种HTML兼容形式的URL编码来进行编码。
	public Request getRequestForm() {
		RequestBody formBody = new FormEncodingBuilder().add("search", "Jurassic Park").build();
		Request request = new Builder().url("https://en.wikipedia.org/w/index.php").post(formBody).build();
		return request;
	}

	private static final String IMGUR_CLIENT_ID = "...";;

	// MultipartBuilder可以构建复杂的请求体，与HTML文件上传形式兼容。多块请求体中每块请求都是一个请求体，可以定义自己的请求头。这些请求头可以用来描述这块请求，例如他的Content-Disposition。如果Content-Length和Content-Type可用的话，他们会被自动添加到请求头中。
	public Request getRequest() {
		RequestBody requestBody = new MultipartBuilder().type(MultipartBuilder.FORM).addPart(Headers.of("Content-Disposition", "form-data; name=\"title\""), RequestBody.create(null, "Square Logo"))
				.addPart(Headers.of("Content-Disposition", "form-data; name=\"image\""), RequestBody.create(MEDIA_TYPE_PNG, new File("website/static/logo-square.png"))).build();

		// RequestBody requestBodyTemp = new MultipartBuilder().type(MultipartBuilder.FORM).addFormDataPart("hello", "android").addFormDataPart("photo", file.getName(), RequestBody.create(null, file))
		// .addPart(Headers.of("Content-Disposition", "form-data; name=\"another\";filename=\"another.dex\""), RequestBody.create(MediaType.parse("application/octet-stream"), file)).build();

		Request request = new Builder().header("Authorization", "Client-ID " + IMGUR_CLIENT_ID).url("https://api.imgur.com/3/image").post(requestBody).build();
		return request;
	}
}
