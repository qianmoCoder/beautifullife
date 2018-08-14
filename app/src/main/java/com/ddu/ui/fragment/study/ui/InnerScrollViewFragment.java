package com.ddu.ui.fragment.study.ui;

import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;

import com.ddu.R;
import com.ddu.icore.refresh.PullToRefreshScrollView;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.iannotation.IElement;

/**
 * Created by yzbzz on 2017/5/25.
 */
@IElement("UI")
public class InnerScrollViewFragment extends DefaultFragment {

    private ScrollView scrollView1;
    private PullToRefreshScrollView mPullToRefresh;
    private WebView mWebView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ui_inner_scrollview;
    }

    @Override
    public void initView() {
        scrollView1 = findViewById(R.id.scroll_1);
        mPullToRefresh = findViewById(R.id.scroll_2);
//        ((PullToRefreshScrollView.MyScrollView) mPullToRefresh.getRefreshableView()).parentScrollView = scrollView1;
        mWebView = findViewById(R.id.wv_other);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                } else {
                    view.loadUrl(request.toString());
                }
                return true;
            }
        });
        mWebView.loadUrl("http://www.baidu.com");
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}
