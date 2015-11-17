package com.beautifullife.core.network.okhttp.request;

import com.beautifullife.core.util.URLUtil;
import com.squareup.okhttp.Request;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2015/11/13.
 */
public class OKHttpGetRequest extends OKHttpRequest {

    protected OKHttpGetRequest(Builder builder) {
        super(builder);
    }

    public static class Builder extends OKHttpRequest.Builder<Builder> {

        @Override
        public OKHttpGetRequest build() {
            return new OKHttpGetRequest(this);
        }
    }

    public static Builder create() {
        return new Builder();
    }

    @Override
    public Request buildRequest() {
        Request.Builder builder = getBuilder();

        String url = getUrl();
        Map<String, String> params = getParams();

        if (params != null && !params.isEmpty()) {
            url = appendUrl(url, params);
        }

        builder.url(url);
        return builder.build();
    }

    private String getParamString(String url, Map<String, String> urlParams) throws UnsupportedEncodingException {

        Map<String, String> tempParams = URLUtil.encodeAllUTF8((HashMap<String, String>) urlParams);

        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : urlParams.entrySet()) {
            if (result.length() > 0) {
                result.append("&");
            }
            result.append((String) entry.getKey());
            result.append("=");
            result.append((String) entry.getValue());
        }

        String temp = result.toString();
        if (!url.contains("?")) {
            url = url + "?" + temp;
        } else {
            url = url + "&" + temp;
        }
        return url;
    }
}
