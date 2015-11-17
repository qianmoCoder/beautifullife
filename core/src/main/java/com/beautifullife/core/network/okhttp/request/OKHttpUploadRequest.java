package com.beautifullife.core.network.okhttp.request;

import android.util.Pair;

import com.beautifullife.core.network.okhttp.response.ProgressHttpResponseHandler;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;

/**
 * Created by admin on 2015/11/13.
 */
public class OKHttpUploadRequest extends OKHttpRequest {

    private final Pair<String, File>[] mFiles;

    private OKHttpUploadRequest(Builder builder) {
        super(builder);
        this.mFiles = builder.files;
    }

    @Override
    public Request buildRequest() {
        ProgressRequestBody requestBody = new ProgressRequestBody(buildRequestBody());
        Request.Builder builder = getBuilder();
        builder.post(requestBody);
        return builder.build();
    }

    private RequestBody buildRequestBody() {
        MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);

        Map<String, String> params = getParams();
        Pair<String, File>[] files = getFiles();

        if (null != params && !params.isEmpty()) {
            for (String key : params.keySet()) {
                multipartBuilder.addFormDataPart(key, params.get(key));
            }
        }

        if (files.length > 0) {
            for (Pair<String, File> item : files) {
                String fileKeyName = item.first;
                File file = item.second;
                String fileName = file.getName();
                RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                multipartBuilder.addFormDataPart(fileKeyName, fileName, fileBody);
            }
        }

        RequestBody requestBody = multipartBuilder.build();
        return requestBody;
    }

    public static class Builder extends OKHttpRequest.Builder<Builder> {

        private Pair<String, File>[] files;

        @Override
        public OKHttpUploadRequest build() {
            return new OKHttpUploadRequest(this);
        }

        public Builder files(Pair<String, File>... files) {
            this.files = files;
            return this;
        }
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    public static Builder create() {
        return new Builder();
    }


    protected void request(ProgressHttpResponseHandler responseHandlerInterface) throws IOException {
        final ProgressHttpResponseHandler progressHttpResponseHandler = responseHandlerInterface;
        RequestBody requestBody = buildRequest().body();
        if (requestBody instanceof ProgressRequestBody) {
            ((ProgressRequestBody) requestBody).setListener(progressHttpResponseHandler);
        }
        super.request(responseHandlerInterface);
    }

    public Pair<String, File>[] getFiles() {
        return mFiles;
    }
}
