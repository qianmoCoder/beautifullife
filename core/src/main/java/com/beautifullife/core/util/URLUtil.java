package com.beautifullife.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2015/9/9.
 */
public class URLUtil {

	public static HashMap<String, String> encodeAll(HashMap<String, String> params, final String charsetName) throws UnsupportedEncodingException {
		return encode(params, charsetName, new Callback() {

			@Override
			public HashMap<String, String> encode(HashMap<String, String> params) throws UnsupportedEncodingException {
				HashMap<String, String> tempParams = (HashMap<String, String>) params.clone();
				for (Map.Entry<String, String> param : params.entrySet()) {
					String key = param.getKey();
					String value = param.getValue();
					tempParams.put(URLEncoder.encode(key, charsetName), URLEncoder.encode(value, charsetName));
				}
				return tempParams;
			}
		});
	}

	public static HashMap<String, String> encodeAllUTF8(HashMap<String, String> parmas) throws UnsupportedEncodingException {
		return encodeAll(parmas, "UTF-8");
	}

	public static HashMap<String, String> encodeValue(HashMap<String, String> params, final String charsetName) throws UnsupportedEncodingException {
		return encode(params, charsetName, new Callback() {

			@Override
			public HashMap<String, String> encode(HashMap<String, String> params) throws UnsupportedEncodingException {
				HashMap<String, String> tempParams = (HashMap<String, String>) params.clone();
				for (Map.Entry<String, String> param : params.entrySet()) {
					String key = param.getKey();
					String value = param.getValue();
					tempParams.put(key, URLEncoder.encode(value, charsetName));
				}
				return tempParams;
			}
		});
	}

	public static HashMap<String, String> encodeValueUTF8(HashMap<String, String> parmas) throws UnsupportedEncodingException {
		return encodeValue(parmas, "UTF-8");
	}

	private static HashMap<String, String> encode(HashMap<String, String> params, String charsetName, Callback callback) throws UnsupportedEncodingException {
		return callback.encode(params);
	}

	public interface Callback {
		HashMap<String, String> encode(HashMap<String, String> params) throws UnsupportedEncodingException;
	}
}
