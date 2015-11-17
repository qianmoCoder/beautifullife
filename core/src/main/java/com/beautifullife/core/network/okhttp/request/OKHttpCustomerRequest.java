package com.beautifullife.core.network.okhttp.request;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import okio.BufferedSink;

/**
 * Created by admin on 2015/11/16.
 */
public class OKHttpCustomerRequest extends OKHttpRequest {

    public static final MediaType MEDIA_TYPE_APP_JSON = MediaType.parse("application/json;charset=utf-8");
    public static final MediaType MEDIA_TYPE_APP_OCTET_STREAM = MediaType.parse("application/octet-stream;charset=utf-8");

    public static final MediaType MEDIA_TYPE_TXT_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    public static final MediaType MEDIA_TYPE_TXT_X_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");

    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    public static final int MEDIA_TYPE_MARKDOWN = 0x01;


    private final String context;

    private OKHttpCustomerRequest(Builder builder) {
        super(builder);
        this.context = builder.context;
    }

    public static class Builder extends OKHttpRequest.Builder<Builder> {

        private String context;

        public Builder context(String context) {
            this.context = context;
            return this;
        }

        @Override
        public OKHttpCustomerRequest build() {
            return new OKHttpCustomerRequest(this);
        }
    }

    public static Builder create() {
        return new Builder();
    }

    @Override
    public Request buildRequest() {
        Request.Builder builder = getBuilder();
        String url = getUrl();
        builder.url(url);
        builder.post(buildeRequestBody());
        return builder.build();
    }

    private RequestBody buildeRequestBody() {
        return null;
    }

    public String getContext() {
        return context;
    }


    /**
     * 使用HTTP POST提交请求到服务。这个例子提交了一个markdown文档到web服务，以HTML方式渲染markdown。因为整个请求体都在内存中，因此避免使用此api提交大文档（大于1MB）
     */
    public RequestBody postString() throws IOException {
//        String postBody = "" + "Releases\n" + "--------\n" + "\n" + " * _1.0_ May 6, 2013\n" + " * _1.1_ June 15, 2013\n" + " * _1.2_ August 11, 2013\n";
        RequestBody formBody = RequestBody.create(MEDIA_TYPE_TXT_X_MARKDOWN, getContext());
        return formBody;
    }

    /**
     * 以流的方式POST提交请求体。请求体的内容由流写入产生。这个例子是流直接写入Okio的BufferedSink。你的程序可能会使用OutputStream，你可以使用BufferedSink.outputStream()来获取。
     */
    public RequestBody postStream() throws Exception {
        RequestBody requestBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return MEDIA_TYPE_TXT_X_MARKDOWN;
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
        return requestBody;
    }

    public RequestBody postFile() throws IOException {
        File file = new File("README.md");
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_TXT_X_MARKDOWN, file);
        return requestBody;
    }

    // 使用FormEncodingBuilder来构建和HTML<form>标签相同效果的请求体。键值对将使用一种HTML兼容形式的URL编码来进行编码。
    public RequestBody postFormParameters() {
        RequestBody formBody = new FormEncodingBuilder().add("search", "Jurassic Park").build();
        return formBody;
    }

    private static final String IMGUR_CLIENT_ID = "...";
    ;

    // MultipartBuilder可以构建复杂的请求体，与HTML文件上传形式兼容。多块请求体中每块请求都是一个请求体，可以定义自己的请求头。这些请求头可以用来描述这块请求，例如他的Content-Disposition。如果Content-Length和Content-Type可用的话，他们会被自动添加到请求头中。
    public RequestBody postMultipartRequest() {
        RequestBody requestBody = new MultipartBuilder().type(MultipartBuilder.FORM).addPart(Headers.of("Content-Disposition", "form-data; name=\"title\""), RequestBody.create(null, "Square Logo"))
                .addPart(Headers.of("Content-Disposition", "form-data; name=\"image\""), RequestBody.create(MEDIA_TYPE_PNG, new File("website/static/logo-square.png"))).build();

        // RequestBody requestBodyTemp = new MultipartBuilder().type(MultipartBuilder.FORM).addFormDataPart("hello", "android").addFormDataPart("photo", file.getName(), RequestBody.create(null, file))
        // .addPart(Headers.of("Content-Disposition", "form-data; name=\"another\";filename=\"another.dex\""), RequestBody.create(MediaType.parse("application/octet-stream"), file)).build();

//        Request request = new Request.Builder().header("Authorization", "Client-ID " + IMGUR_CLIENT_ID).url("https://api.imgur.com/3/image").post(requestBody).build();
        return requestBody;
    }

    public void uploadFile(Map<String, String> map, List<String> paths) {
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        for (String key : map.keySet()) {
            builder.addFormDataPart(key, map.get(key));
        }

        for (String path : paths) {
            builder.addFormDataPart("upload", null, RequestBody.create(MEDIA_TYPE_PNG, new File(path)));
        }
    }

    // OAuth
    private Call buildCall(Request request) {
//		OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer("CONSUMER_KEY","CONSUMER_SECRET");
//		consumer.setTokenWithSecret("token", "secret");
//
//		mOkHttpClient.interceptors().add(new SigningInterceptor(consumer));
//		request = (Request)consumer.sign(request).unwrap();
        Call call = okHttpManager.getOkHttpClient().newCall(request);
        return call;
    }
}
