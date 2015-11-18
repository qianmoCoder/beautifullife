package com.dream.beautifullife.network;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.beautifullife.core.network.okhttp.request.OKHttpDownloadRequest;
import com.beautifullife.core.network.okhttp.request.OKHttpGetRequest;
import com.beautifullife.core.network.okhttp.request.OKHttpPostRequest;
import com.beautifullife.core.network.okhttp.request.OKHttpRequest;
import com.beautifullife.core.network.okhttp.request.OKHttpUploadRequest;
import com.beautifullife.core.network.okhttp.response.ProgressHttpResponseHandler;
import com.beautifullife.core.network.okhttp.response.ResponseHandlerInterface;
import com.beautifullife.core.util.CollectionUtil;
import com.beautifullife.core.util.MD5Utils;
import com.beautifullife.core.util.URLUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author admin
 */
public class NetworkManager {

    public static OKHttpGetRequest get(Context context) throws Exception {

        OKHttpGetRequest.Builder request = (OKHttpGetRequest.Builder) getRequest(context, OKHttpGetRequest.create());
        return request.build();
    }

    public static OKHttpPostRequest post(Context context) throws Exception {
        OKHttpPostRequest.Builder request = (OKHttpPostRequest.Builder) getRequest(context, OKHttpPostRequest.create());
        return request.build();
    }

    public static void post(Context context, String url, HashMap<String, String> params, String md5Param, boolean isEncode, ResponseHandlerInterface responseHandlerInterface) throws UnsupportedEncodingException {
        HashMap<String, String> tempParams;

        if (params == null) {
            tempParams = CollectionUtil.hashMap();
        } else {
            if (isEncode) {
                tempParams = URLUtil.encodeValueUTF8(params);
            } else {
                tempParams = (HashMap) params.clone();
            }
        }

        if (!TextUtils.isEmpty(md5Param)) {
            if (params != null) {
                String validate = MD5Utils.validate(tempParams);
                tempParams.put(md5Param, URLEncoder.encode(validate, "UTF-8"));
            }
        }
    }

    public OKHttpDownloadRequest download(Context context, ProgressHttpResponseHandler responseHandlerInterface) throws Exception {
        OKHttpDownloadRequest.Builder request = (OKHttpDownloadRequest.Builder) getRequest(context, OKHttpDownloadRequest.create());
        return request.build();
    }

    public OKHttpUploadRequest upload(Context context, ResponseHandlerInterface responseHandlerInterface)
            throws Exception {
        OKHttpUploadRequest.Builder request = (OKHttpUploadRequest.Builder) getRequest(context, OKHttpUploadRequest.create());
        return request.build();
    }

    private static OKHttpRequest.Builder getRequest(Context context, OKHttpRequest.Builder builder) throws Exception {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);

        HashMap<String, String> headers = CollectionUtil.hashMap();
        headers.put("versionName", info.versionName);
        headers.put("platform", "android");
        builder.headers(headers);

        return builder;
    }
//
//    private OKHttpRequest getRequest(Context context, OKHttpRequest request) throws Exception {
//        PackageManager manager = context.getPackageManager();
//        PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
//
//        HashMap<String, String> headers = CollectionUtil.hashMap();
//        headers.put("versionName", info.versionName);
//        headers.put("platform", "android");
//
//        HashMap<String, String> tempParams = CollectionUtil.hashMap();
//
//        if (params == null) {
//            tempParams = new HashMap<String, String>();
//        } else {
//            if (isEncode) {
//                tempParams = URLUtil.encodeValueUTF8(params);
//            } else {
//                tempParams = (HashMap) params.clone();
//            }
//        }
//
//        if (!TextUtils.isEmpty(md5Param)) {
//            if (params != null) {
//                String validate = MD5Utils.validate(tempParams);
//                tempParams.put(md5Param, URLEncoder.encode(validate, "UTF-8"));
//            }
//        }
//        return OKHttpRequest.Builder.create().url(url).headers(headers).params(tempParams);
//    }
//
//    public OKHttpManager getOkHttpManager() {
//        return mOkHttpManager;
//    }
//
//    public void cancel(Object tag) {
//        mOkHttpManager.cancel(tag);
//    }
//
//    private interface CallBack {
//
//        OKHttpRequest getRequest(String url);
//    }

}
