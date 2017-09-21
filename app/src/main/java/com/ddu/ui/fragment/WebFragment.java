package com.ddu.ui.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.LinearLayout;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.util.PopupUtils;
import com.ddu.icore.util.UrlUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.regex.Matcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
        mWebView.loadUrl("http://miop.test.etcp.cn");
//        mWebView.loadUrl("http://fe.test.etcp.cn/api/app/etcpjsapi.html");
//        mWebView.addJavascriptInterface(new WebAppInterface(mContext), "IcoreSBridge");
        mWebView.addJavascriptInterface(this, "ETCPSBridge");
        initTitle();
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mWebView.loadUrl("about:blank");
//                mWebView.clearView();
//                if (isLoadFirst) {
//                    mWebView.loadUrl("http://webapp.test.etcp.cn/operateActive/operatelist");
//                } else {
//                    mWebView.loadUrl("http://fe.test.etcp.cn/api/app/etcpjsapi.html");
//                }
                mWebView.reload();
                isLoadFirst = !isLoadFirst;
            }
        });
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
        //{“type":"webGotoApp","value":"etcp://6","callback":"ETCPSBridgeWebGotoApp6"}
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
//            view.loadUrl("javascript:ETCPSBridge.resize(JSON.stringify({type:'getHeight',height:document.body.getBoundingClientRect().height}))");
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
