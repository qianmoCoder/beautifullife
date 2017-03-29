package com.ddu.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ddu.R;
import com.ddu.icore.aidl.GodIntent;
import com.ddu.logic.common.LogicActions;
import com.ddu.logic.common.ObserverManager;
import com.ddu.logic.common.UIObserver;
import com.ddu.ui.helper.WebAppInterface;

import org.json.JSONObject;


public class WebActivity extends Activity implements UIObserver {


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
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    // 滚动条消失
                }
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                Log.v("lhz", "messge: " + message);
                Log.v("lhz", "defaultValue: " + defaultValue);
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

        });
        //http://www.wdxhb.com/m/etcpjsapi.html
        webView.loadUrl("https://prism.zhongan.com/activities/refundcard/index?channel=c4084540");
        webView.addJavascriptInterface(new WebAppInterface(this), "ETCPSBridge");
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
        ObserverManager.getInstance().registerObserver(LogicActions.TEST_ACTION, this);
    }

    @Override
    public void onReceiverNotify(@NonNull GodIntent godIntent) {
        findViewById(R.id.rl_title_bar).setVisibility(View.GONE);
        String method = godIntent.getString("method");
        webView.loadUrl("javascript:" + method + "(" + "" + ")");
    }
}
