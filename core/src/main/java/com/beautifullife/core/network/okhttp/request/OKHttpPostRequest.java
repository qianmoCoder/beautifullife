package com.beautifullife.core.network.okhttp.request;

import android.util.Pair;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 2015/11/13.
 */
public class OKHttpPostRequest extends OKHttpRequest {

    private OKHttpPostRequest(Builder builder) {
        super(builder);
    }

    public static class Builder extends OKHttpRequest.Builder<Builder> {

        @Override
        public OKHttpPostRequest build() {
            return new OKHttpPostRequest(this);
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
            List<Pair<String, String>> res = mapToParams(params);
            FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
            for (Pair<String, String> param : res) {
                formEncodingBuilder.add(param.first, param.second);
            }
            RequestBody requestBody = formEncodingBuilder.build();
            builder.post(requestBody);
        }

        builder.url(url);
        return builder.build();
    }

    private List<Pair<String, String>> mapToParams(Map<String, String> params) {
        List<Pair<String, String>> res = new ArrayList<>();
        if (params == null)
            return res;
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            res.add(Pair.create(entry.getKey(), entry.getValue()));
        }
        return res;
    }
}
