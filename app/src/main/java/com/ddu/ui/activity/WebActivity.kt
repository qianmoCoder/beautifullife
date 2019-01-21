package com.ddu.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.ddu.R
import com.ddu.app.BaseApp
import com.ddu.icore.aidl.GodIntent
import com.ddu.icore.common.IObserver
import com.ddu.icore.common.ObserverManager
import com.ddu.icore.logic.Actions
import com.ddu.icore.util.UrlUtils
import com.google.gson.JsonObject
import org.json.JSONObject
import java.net.URISyntaxException


class WebActivity : Activity(), IObserver<GodIntent> {


    private var webView: WebView? = null
    private var context: Context? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        init()
        registerObserver()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init() {
        // html data
        context = this
        webView = findViewById(R.id.webview)
        val settings = webView!!.settings
        settings.javaScriptEnabled = true
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.setSupportZoom(true)
        settings.builtInZoomControls = true

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
        //            public boolean onJsPrompt(WebView view, String data, String message, String defaultValue, JsPromptResult result) {
        //                Log.v("lhz", "messge: " + message);
        //                Log.v("lhz", "defaultValue: " + defaultValue);
        //                return super.onJsPrompt(view, data, message, defaultValue, result);
        //            }
        //        });
        //        webView.setWebViewClient(new WebViewClient() {
        //            @Override
        //            public boolean shouldOverrideUrlLoading(WebView view, String data) {
        //                boolean ret = startActivityForUrl(data);
        //                return ret;
        //            }
        //
        //            @Override
        //            public void onPageStarted(WebView view, String data, Bitmap favicon) {
        //                super.onPageStarted(view, data, favicon);
        //            }
        //
        //            @Override
        //            public void onPageFinished(WebView view, String data) {
        //                super.onPageFinished(view, data);
        //            }
        //
        //        });

//        val am = assets
//        try {
//            val `is` = am.open("protocol.html")
//            val buffer = ByteArray(10000)
//            `is`.read(buffer)
//
//            val data = String(buffer,  Charsets.UTF_8)
//            webView!!.loadData(data, "text/html; charset=UTF-8", null)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
        val url = intent.getStringExtra("url")
        webView!!.webViewClient = webViewClient
        webView!!.loadUrl(url)
    }

    fun getUserStatusInfo(jsonString: JSONObject) {
        try {
            val jsonObject = JsonObject()
            jsonObject.addProperty("userId", "10086")


            val callBack = jsonString.getString("callback")
            if (!TextUtils.isEmpty(callBack)) {
                webView!!.loadUrl("javascript:" + callBack + "(" + jsonObject.toString() + ")")
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    internal inner class JavascriptInterface {

        @android.webkit.JavascriptInterface
        operator
                /** 解决Android 17（包括17）之后js无法调用Android方法 */
        fun invoke(jsonString: String) {

            BaseApp.post(Runnable {
                try {
                    Log.v("lhz", "json: " + jsonString)
                    getUserStatusInfo(JSONObject(jsonString))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })

            //            try {
            //                Log.v("lhz", json);
            //                JSONObject jsonObject = new JSONObject(json);
            //                String type = jsonObject.getString("type");
            //                Log.v("lhz", "type: " + type);
            //            } catch (Exception e) {
            //                Log.v("lhz", e.toString());
            //            }


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

    override fun registerObserver() {
        ObserverManager.getInstance().registerObserver(Actions.TEST_ACTION, this)
    }

    override fun onReceiverNotify(godIntent: GodIntent) {
        findViewById<View>(R.id.rl_title_bar).visibility = View.GONE
        val method = godIntent.getString("method")
        webView!!.loadUrl("javascript:$method()")
    }

    private fun startActivityForUrl(url: String): Boolean {
        var intent: Intent
        // perform generic parsing of the URI to turn it into an Intent.
        try {
            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
        } catch (ex: URISyntaxException) {
            Log.w("Browser", "Bad URI " + url + ": " + ex.message)
            return false
        }

        // check whether the intent can be resolved. If not, we will see
        // whether we can download it from the Market.

        if (packageManager.resolveActivity(intent, 0) == null) {
            val packagename = intent.`package`
            if (packagename != null) {
                try {
                    intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pname:" + packagename))
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                    finish()
                    return false
                }

                // before leaving BrowserActivity, close the empty child tab.
                // If a new tab is created through JavaScript open to load this
                // data, we would like to close it as we will load this data in a
                // different Activity.
                // mController.closeEmptyTab();
                return true
            } else {
                return false
            }
        }

        // sanitize the Intent, ensuring web pages can not bypass browser
        // security (only access to BROWSABLE activities).
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.component = null
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
        val m = UrlUtils.ACCEPTED_URI_SCHEMA.matcher(url)
        if (m.matches()) { //&& !isSpecializedHandlerAvailable(intent)) {
            return false
        }
        try {
            intent.putExtra(UrlUtils.EXTRA_DISABLE_URL_OVERRIDE, true)
            if (startActivityIfNeeded(intent, -1)) {
                // before leaving BrowserActivity, close the empty child tab.
                // If a new tab is created through JavaScript open to load this
                // data, we would like to close it as we will load this data in a
                // different Activity.
                // mController.closeEmptyTab();
                return true
            }
        } catch (ex: ActivityNotFoundException) {
            // ignore the error. If no application can handle the URL,
            // eg about:blank, assume the browser can handle it.
        }

        return false
    }

    private val webViewClient = object : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        }

        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            handler.proceed()
        }


        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)

        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

    }
}
