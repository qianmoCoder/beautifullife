package com.ddu.ui.fragment;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ddu.R;
import com.ddu.app.App;
import com.ddu.app.GlideApp;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.util.PopupUtils;
import com.ddu.icore.util.UrlUtils;
import com.ddu.ui.helper.WebAppInterface;
import com.ddu.util.HttpUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by yzbzz on 16/4/6.
 */
public class WebFragment extends DefaultFragment {

    @Nullable
//    @BindView(R.id.fl_web)
            FrameLayout flWeb;
    //    @Nullable
//    @BindView(R.id.pb_web)
//    ProgressBar pbWeb;
    @Nullable
    @BindView(R.id.wv_web)
    WebView mWebView;

    @BindView(R.id.btn_reload)
    Button btnReload;

    @BindView(R.id.iv_place)
    ImageView iv_place;

    @BindView(R.id.pb_loading)
    ProgressBar progressBar;

//    @BindView(R.id.iv_progress)
//    ImageView iv_progress;

    private WebSettings mWebSettings;
    @Nullable
    private String title;
    @Nullable
    private String url;

    private Unbinder unbinder;


    @NonNull
    public static WebFragment newInstance(String title, String url) {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (null != bundle) {
            title = bundle.getString("title");
            url = bundle.getString("url");
        }
    }

    private boolean isLoadFirst;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_web;
    }

    @Override
    public void initView() {
        unbinder = ButterKnife.bind(this, mView);

        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebSettings.setSupportZoom(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);

        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setAppCacheEnabled(true);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setSavePassword(true);
        mWebSettings.setSupportZoom(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebSettings.setUseWideViewPort(true);

        mWebView.setWebViewClient(webViewClient);
        mWebView.setWebChromeClient(webChromeClient);

//        reload("protocol.html");
        mWebView.addJavascriptInterface(new WebAppInterface(mContext), "SBridge");
        mWebView.addJavascriptInterface(this, "SBridge");
        initTitle();
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post1();
            }
        });

//        final ObjectAnimator anim = ObjectAnimator.ofInt(iv_place, "ImageLevel", 0, 10000);
//        anim.setDuration(800);
//        anim.setRepeatCount(ObjectAnimator.INFINITE);
//        anim.start();
//
//        iv_progress.setImageResource(R.drawable.glide_rotate);
//        iv_place.setImageResource(R.drawable.glide_rotate);
        GlideApp.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2760457749,4161462131&fm=27&gp=0.jpg").into(new ImageViewTarget<Drawable>(iv_place) {
            @Override
            protected void setResource(@Nullable Drawable resource) {
                Log.v("lhz", "setResource");
            }

            @Override
            public void onResourceReady(Drawable resource, @Nullable Transition<? super Drawable> transition) {
                super.onResourceReady(resource, transition);
//                Log.v("lhz", "onResourceReady");
                iv_place.setImageDrawable(resource);
//                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
//                Log.v("lhz", "onLoadFailed");
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                super.onLoadCleared(placeholder);
//                Log.v("lhz", "onLoadCleared");
            }
        });

//        GlideApp.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2760457749,4161462131&fm=27&gp=0.jpg").listener(new RequestListener<Drawable>() {
//            @Override
//            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                Log.v("lhz", "onLoadFailed");
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                Log.v("lhz", "onResourceReady");
//                return false;
//            }
//        }).into(new ImageViewTarget<Drawable>(iv_place) {
//            @Override
//            protected void setResource(@Nullable Drawable resource) {
//                iv_place.setImageDrawable(resource);
//                Log.v("lhz", "setResource");
//            }
//        });
    }

    private void post1() {
//        final HashMap<String, Object> params = new LinkedHashMap<String, Object>();
//        params.put("scene", "2759");
////        params.put("page", "pages/main/main");
////        params.put("width", "430");
////        params.put("auto_color", "false");
////        JSONObject jsonObject = new JSONObject();
////        try {
////            jsonObject.put("r", "0");
////            jsonObject.put("g", "0");
////            jsonObject.put("b", "0");
////            String line_color = jsonObject.toString();
////            Log.v("lhz", "line_color: " + line_color);
////            params.put("line_color", line_color);
////        } catch (Exception e) {
////
////        }
//        String accessToken = "YXGzQVOUIrdZxqMcoL3LeLtDZrm7nJZwN1N2CvzpmmII9aXYQs3S2LuG7gNjtbTHBNH_I8ry7z8La-Fx-eah7v5kq_zqDZhyq3IsIdENayxkNS56G5wkHlYBBE91UcA9PKQbAJAYTZ";
//        final String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken;
//        Log.v("lhz", "url: " + url);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                HttpUtils.post(url, params);
//            }
//        }).start();
        String accessToken = "gFonXmV4ENkG2250CxYE96_SyxpSYMWMtp6naQbVHBpak2oYjUcDZHhf8rqUoThUN3yYaFcWURXNKuWG1CVaF0qf2X4iZONosv_WWqwjeY0WKDhACAESL";
        post(accessToken);
    }

    private void qrCode(String accessToken) {
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        String url = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=" + accessToken;
        Log.v("lhz", "url: " + url);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        Request.Builder builder = new Request.Builder();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("path", "pages/index");
            jsonObject.put("width", 430);

        } catch (Exception e) {

        }
        String line_color = jsonObject.toString();
        Log.v("lhz", "line_color: " + line_color);
        final RequestBody requestBody = RequestBody.create(JSON, line_color);
        builder.addHeader("content-type", "application/json;charset:utf-8");
        builder.post(requestBody);
        builder.url(url);
        final Request request = builder.build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("lhz", "e: " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();

                saveAdData(responseBody.byteStream());
//                Log.v("lhz", "string: " + string);
            }
        });
    }

    public void excute(final String method, final String json) {
        App.post(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl("javascript:" + method + "(" + json + ")");
            }
        });
    }


    private void post(String accessToken) {
        String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken;
        Log.v("lhz", "url: " + url);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        Request.Builder builder = new Request.Builder();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("scene", "park2011");
            jsonObject.put("page", "pages/main/main");
            jsonObject.put("width", 1024);
            jsonObject.put("auto_color", true);

            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("r", "0");
            jsonObject1.put("g", "0");
            jsonObject1.put("b", "0");

            jsonObject.put("line_color", jsonObject1);
        } catch (Exception e) {

        }
        String line_color = jsonObject.toString();
        Log.v("lhz", "data: " + line_color);
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");

        final RequestBody requestBody = RequestBody.create(JSON, line_color);
        builder.addHeader("content-type", "application/json;charset:utf-8");
        builder.url(url);
        builder.post(requestBody);

        final Request request = builder.build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("lhz", "e: " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();

//                String string = responseBody.string();
                saveAdData(responseBody.byteStream());
//                Log.v("lhz", "string: " + string);
            }
        });

    }

    private void get() {
        String getUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxc07f9d67923d676d&secret=75a05e5d99fa43189c65c81d96b2bf23";
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        final Request request = builder.build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("lhz", "e: " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();

                String string = responseBody.string();
//                saveAdData(responseBody.byteStream());
                Log.v("lhz", "string: " + string);
            }
        });
    }

    private void saveAdData(InputStream inputStream) {
        boolean isGranted = ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        if (!isGranted) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            HttpUtils.saveAd(inputStream);
        }
    }


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

    private void getQRCode() {

    }


    private void reload(String xml) {
        mWebView.clearView();
        mWebView.measure(10, 10);
        AssetManager am = mContext.getAssets();
        try {
            InputStream is = am.open(xml);
            byte[] buffer = new byte[10000];
            is.read(buffer);
            String data = new String(buffer, "utf-8");
            mWebView.loadData(data, "text/html; charset=UTF-8", null);
            mWebView.requestLayout();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void resize(final String json) {
        Log.v("lhz", "json: " + json);
        mWebView.post(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    String type = jsonObject.getString("type");
                    double height = jsonObject.getDouble("height");
                    Log.v("lhz", "height: " + height + " " + mWebView.getHeight());
                    int heightT = (int) (height * getResources().getDisplayMetrics().density);
                    mWebView.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, heightT));
                } catch (Exception e) {

                }
            }
        });
    }

    public void initTitle() {
        setDefaultTitle(title);
        setRightImg(R.drawable.ic_more_add_selector, new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                showPopupWindow(v);
            }
        });
        getTitleBar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        setTitleBarOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.pageUp(true);
//                mWebView.scrollTo(0,0);
            }
        });
    }

    private void showPopupWindow(@NonNull View v) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragemt_web_more_popup, null);
//        PopupUtils.showTopRightPopupWindow(baseActivity, v, view);
//        PopupUtils.showPopupWindow(view).showAtLocation(v.getParent(), Gravity.RIGHT,);
        PopupUtils.showPopupWindow(view).showAsDropDown(v);
    }

    @NonNull
    private WebChromeClient webChromeClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
//            pbWeb.setProgress(newProgress);
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (!TextUtils.isEmpty(title)) {
                setDefaultTitle(title);
            }
        }

    };

    @NonNull
    private WebViewClient webViewClient = new WebViewClient() {

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//            super.onReceivedSslError(view, handler, error);
            handler.proceed();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
//            pbWeb.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(@NonNull WebView view, String url) {
//            view.loadUrl("javascript:MyApp.resize(document.querySelector('body').offsetHeight);");
//            view.loadUrl("javascript:MyApp.resize(document.body.getBoundingClientRect().height)");
            super.onPageFinished(view, url);
//            pbWeb.setVisibility(View.GONE);
            String title = view.getTitle();
            if (!TextUtils.isEmpty(title)) {
                setDefaultTitle(title);
            }
            mWebView.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(@NonNull WebView view, String url) {
            view.loadUrl(url);
            return true;
//            return super.shouldOverrideUrlLoading(view, url);
        }

    };

    private boolean startActivityForUrl(@NonNull String url) {
        Intent intent;
        // perform generic parsing of the URI to turn it into an Intent.
        try {
            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
        } catch (URISyntaxException ex) {
            Log.w("Browser", "Bad URI " + url + ": " + ex.getMessage());
            return false;
        }

        // check whether the intent can be resolved. If not, we will see
        // whether we can download it from the Market.

        if (mContext.getPackageManager().resolveActivity(intent, 0) == null) {
            String packagename = intent.getPackage();
            if (packagename != null) {
                try {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pname:" + packagename));
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    baseActivity.finish();
                    return false;
                }
                // before leaving BrowserActivity, close the empty child tab.
                // If a new tab is created through JavaScript open to load this
                // url, we would like to close it as we will load this url in a
                // different Activity.
                // mController.closeEmptyTab();
                return true;
            } else {
                return false;
            }
        }

        // sanitize the Intent, ensuring web pages can not bypass browser
        // security (only access to BROWSABLE activities).
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setComponent(null);
        // Re-use the existing tab if the intent comes back to us
        // if (tab != null) {
        // if (tab.getAppId() == null) {
        // tab.setAppId(mActivity.getPackageName() + "-" + tab.getId());
        // }
        // intent.putExtra(Browser.EXTRA_APPLICATION_ID, tab.getAppId());
        // }
        // Make sure webkit can handle it internally before checking for specialized
        // handlers. If webkit can't handle it internally, we need to call
        // startActivityIfNeeded
        Matcher m = UrlUtils.ACCEPTED_URI_SCHEMA.matcher(url);
        if (m.matches()) { //&& !isSpecializedHandlerAvailable(intent)) {
            return false;
        }
        try {
            intent.putExtra(UrlUtils.EXTRA_DISABLE_URL_OVERRIDE, true);
            if (baseActivity.startActivityIfNeeded(intent, -1)) {
                // before leaving BrowserActivity, close the empty child tab.
                // If a new tab is created through JavaScript open to load this
                // url, we would like to close it as we will load this url in a
                // different Activity.
                // mController.closeEmptyTab();
                return true;
            }
        } catch (ActivityNotFoundException ex) {
            // ignore the error. If no application can handle the URL,
            // eg about:blank, assume the browser can handle it.
        }

        return false;
    }

    private boolean isSpecializedHandlerAvailable(Intent intent) {
        PackageManager pm = mContext.getPackageManager();
        List<ResolveInfo> handlers = pm.queryIntentActivities(intent, PackageManager.GET_RESOLVED_FILTER);
        if (handlers == null || handlers.size() == 0) {
            return false;
        }
        for (ResolveInfo resolveInfo : handlers) {
            IntentFilter filter = resolveInfo.filter;
            if (filter == null) {
                // No intent filter matches this intent?
                // Error on the side of staying in the browser, ignore
                continue;
            }
            if (filter.countDataAuthorities() == 0 && filter.countDataPaths() == 0) {
                // Generic handler, skip
                continue;
            }
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mWebView) {
            if (null != flWeb) {
                flWeb.removeView(mWebView);
            }
            mWebView.removeAllViews();
            mWebView.destroy();
        }
        unbinder.unbind();
    }
}
