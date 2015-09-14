package com.dream.beautifullife.network;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建日期：2015年9月14日 版权所有 悦畅科技有限公司。 保留所有权利。<br>
 * 项目名：悟空停车 - Android客户端<br>
 * 描述：
 * 
 * @author admin
 */
public class OKHttpRequest {

	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";

	private String url;
	private Object tag;
	private HashMap<String, String> headers;
	private HashMap<String, String> params;
	private String method;

	private static OKHttpRequest okHttpRequest;

	private OKHttpRequest(String url, String method) {
		this.url = url;
		this.method = method;
	}

	public static OKHttpRequest getInstanceByGet(String url) {
		if (okHttpRequest == null) {
			okHttpRequest = new OKHttpRequest(url, METHOD_GET);
		}
		return okHttpRequest;
	}

	public static OKHttpRequest getInstanceByPost(String url) {
		if (okHttpRequest == null) {
			okHttpRequest = new OKHttpRequest(url, METHOD_POST);
		}
		return okHttpRequest;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the tag
	 */
	public Object getTag() {
		return tag;
	}

	/**
	 * @param tag
	 *            the tag to set
	 */
	public void setTag(Object tag) {
		this.tag = tag;
	}

	/**
	 * @return the heaers
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * @param heaers
	 *            the heaers to set
	 */
	public void setHeaders(HashMap<String, String> heaers) {
		this.headers = heaers;
	}

	/**
	 * @return the params
	 */
	public HashMap<String, String> getParams() {
		return params;
	}

	/**
	 * @param params
	 *            the params to set
	 */
	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

}
