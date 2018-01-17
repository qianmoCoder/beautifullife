package com.ddu.sdk.network;

import java.util.Map;

import okhttp3.HttpUrl;

/**
 * Created by yzbzz on 2018/1/17.
 */

public class OkHttpUtils {
    
    public static String appendUrl(String url, Map<String, String> urlParams) {
        if (null == url) {
            return "";
        }
        if (urlParams == null || urlParams.size() <= 0) {
            return url;
        }
        HttpUrl httpUrl = HttpUrl.parse(url);
        if (null == httpUrl) {
            return url;
        }
        HttpUrl.Builder urlBuilder = httpUrl.newBuilder();
        for (Map.Entry<String, String> entry : urlParams.entrySet()) {
            urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        return urlBuilder.build().toString();
    }
}
