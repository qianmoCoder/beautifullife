package com.ddu.ui.fragment

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.*
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import kotlinx.android.synthetic.main.fragment_web.*

/**
 * Created by yzbzz on 16/4/6.
 */
class WebFragment : DefaultFragment() {

    private var mWebSettings: WebSettings? = null
    private var mTitle = ""
    private var url = ""

    override fun initData(savedInstanceState: Bundle?) {
        arguments?.apply {
            mTitle = getString("mTitle", "")
            url = getString("url", "")
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_web
    }

    override fun initView() {
        mWebSettings = wv_web.settings
        mWebSettings?.apply {
            javaScriptEnabled = true
            cacheMode = WebSettings.LOAD_NO_CACHE
            setSupportZoom(true)
            useWideViewPort = true
            loadWithOverviewMode = true

            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            cacheMode = WebSettings.LOAD_NO_CACHE
            domStorageEnabled = true
            databaseEnabled = true
            setAppCacheEnabled(true)
            allowFileAccess = true
            savePassword = true
            setSupportZoom(true)
            builtInZoomControls = true
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
            useWideViewPort = true
        }

        wv_web.webViewClient = webViewClient
        wv_web.webChromeClient = webChromeClient

        initTitle()
    }

    private fun initTitle() {
        setDefaultTitle(mTitle)
        setRightImg(R.drawable.ic_more_add_selector, object : View.OnClickListener {
            override fun onClick(v: View?) {
                wv_web.pageUp(true)
            }
        })

        setTitleBarOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                wv_web.pageUp(true)
            }
        })
    }

    private val webChromeClient = object : WebChromeClient() {

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            pb_web.progress = newProgress
        }

        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            if (!TextUtils.isEmpty(title)) {
                setDefaultTitle(title)
            }
        }

    }

    private val webViewClient = object : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            pb_web.visibility = View.VISIBLE
        }

        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            handler.proceed()
        }


        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            pb_web.visibility = View.GONE
            val title = view.title
            if (!TextUtils.isEmpty(title)) {
                setDefaultTitle(title)
            }
            wv_web.visibility = View.VISIBLE
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if (null != wv_web) {
            wv_web.removeAllViews()
            wv_web.destroy()
        }
    }

    companion object {

        fun newInstance(title: String, url: String): WebFragment {
            val fragment = WebFragment()
            val args = Bundle()
            args.putString("mTitle", title)
            args.putString("url", url)
            fragment.arguments = args
            return fragment
        }
    }
}
