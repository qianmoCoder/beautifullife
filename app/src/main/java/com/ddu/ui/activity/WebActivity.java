package com.ddu.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.ddu.R;
import com.ddu.icore.aidl.GodIntent;
import com.ddu.icore.common.IObserver;
import com.ddu.icore.common.ObserverManager;
import com.ddu.icore.logic.Actions;
import com.ddu.icore.util.UrlUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.regex.Matcher;


public class WebActivity extends Activity implements IObserver<GodIntent> {


    private WebView webView;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_web);
        init();
        registerObserver();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        // html url
        context = this;
        webView = (WebView) findViewById(R.id.wv_web);
//        WebSettings settings = webView.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        settings.setSupportZoom(true);
//        settings.setBuiltInZoomControls(true);

//        webView.setWebChromeClient(new WebChromeClient() {
//
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//                if (newProgress == 100) {
//                    // 滚动条消失
//                }
//            }
//
//            @Override
//            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
//                Log.v("lhz", "messge: " + message);
//                Log.v("lhz", "defaultValue: " + defaultValue);
//                return super.onJsPrompt(view, url, message, defaultValue, result);
//            }
//        });
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                boolean ret = startActivityForUrl(url);
//                return ret;
//            }
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//            }
//
//        });
        //http://www.wdxhb.com/m/etcpjsapi.html
//        webView.loadUrl("https://prism.zhongan.com/activities/refundcard/index?channel=c4084540");

        AssetManager am = getAssets();
        try {
            InputStream is = am.open("protocol.html");
            byte[] buffer = new byte[10000];
            is.read(buffer);
            String data = new String(buffer, "utf-8");
            webView.loadData(data, "text/html; charset=UTF-8", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        webView.addJavascriptInterface(new WebAppInterface(this), "ETCPSBridge");
    }

    class JavascriptInterface {

        @SuppressWarnings("unused")
        @android.webkit.JavascriptInterface
        /** 解决Android 17（包括17）之后js无法调用Android方法*/
        public void invoke(String json) {
            try {
                Log.v("lhz", json);
                JSONObject jsonObject = new JSONObject(json);
                String type = jsonObject.getString("type");
                Log.v("lhz", "type: " + type);
            } catch (Exception e) {
                Log.v("lhz", e.toString());
            }


//            try {
//                // 解析js传递过来的json串
//                JSONObject mJson = new JSONObject(json);
//                String name = mJson.optString("name");
////				Toast.makeText(context, name, Toast.LENGTH_LONG).show();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }

//        @SuppressWarnings("unused")
//        @android.webkit.JavascriptInterface
//        /** 解决Android 17（包括17）之后js无法调用Android方法*/
//        public void invoke(Object json) {
//            Log.v("lhz", json.toString());
////            try {
////                // 解析js传递过来的json串
////                JSONObject mJson = new JSONObject(json);
////                String name = mJson.optString("name");
//////				Toast.makeText(context, name, Toast.LENGTH_LONG).show();
////            } catch (JSONException e) {
////                e.printStackTrace();
////            }
//        }
    }

    @Override
    public void registerObserver() {
        ObserverManager.getInstance().registerObserver(Actions.TEST_ACTION, this);
    }

    @Override
    public void onReceiverNotify(@NonNull GodIntent godIntent) {
        findViewById(R.id.rl_title_bar).setVisibility(View.GONE);
        String method = godIntent.getString("method");
        webView.loadUrl("javascript:" + method + "(" + "" + ")");
    }

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

        if (getPackageManager().resolveActivity(intent, 0) == null) {
            String packagename = intent.getPackage();
            if (packagename != null) {
                try {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pname:" + packagename));
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    finish();
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
            if (startActivityIfNeeded(intent, -1)) {
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
}
