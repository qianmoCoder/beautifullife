package com.ddu.ui.fragment

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.*
import com.ddu.R
import com.ddu.icore.common.clipText
import com.ddu.icore.common.startBrowser
import com.ddu.icore.dialog.DefaultBottomDialogFragment
import com.ddu.icore.entity.BottomItemEntity
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.icore.util.ToastUtils
import com.ddu.ui.dialog.FontSettingDialog
import kotlinx.android.synthetic.main.fragment_web.*
import org.jetbrains.anko.support.v4.ctx

/**
 * Created by yzbzz on 16/4/6.
 */
class WebFragment : DefaultFragment() {

    private var mWebSettings: WebSettings? = null
    private var mTitle = ""
    private var mUrl = ""

    override fun initData(savedInstanceState: Bundle?) {
        arguments?.apply {
            mTitle = getString("mTitle", "")
            mUrl = getString("url", "")
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

        wv_web.loadUrl(mUrl)
    }

    private fun initTitle() {
        setDefaultTitle(mTitle)
        setRightImg(R.drawable.ic_more_add_selector, object : View.OnClickListener {
            override fun onClick(v: View?) {
                showBottomDialog()
            }
        })

        setTitleBarOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                wv_web.pageUp(true)
            }
        })
    }

    private fun showBottomDialog() {
        val entity = getEntity()
        val dialog = DefaultBottomDialogFragment.newInstance("操作", entity, { _, _, _ ->
        })
        dialog.show(fragmentManager, "")
    }

    private fun getEntity(): List<BottomItemEntity> {
        val entities = mutableListOf<BottomItemEntity>()

        val sendEntity = BottomItemEntity("发送给朋友", R.drawable.bottom_icon_send) {

        }
        entities.add(sendEntity)

        val favoriteEntity = BottomItemEntity("收藏", R.drawable.bottom_icon_favorite)
        entities.add(favoriteEntity)

        val searchEntity = BottomItemEntity("搜索页面内容", R.drawable.bottom_icon_search)
        entities.add(searchEntity)

        val linkEntity = BottomItemEntity("复制链接", R.drawable.bottom_icon_link) {
            ctx.clipText(mUrl)
            ToastUtils.showToast("已复制到剪贴板")
        }
        entities.add(linkEntity)

        val browserEntity = BottomItemEntity("在浏览器打开", R.drawable.bottom_icon_browser) {
            ctx.startBrowser(mUrl)
        }
        entities.add(browserEntity)

        val fontEntity = BottomItemEntity("字体", R.drawable.bottom_icon_font) {
            // SMALLEST(50),
//            SMALLER(75),
//            NORMAL(100),
//            LARGER(150),
//            LARGEST(200);
//            mWebSettings?.textZoom = 50
            val dialog = FontSettingDialog()
            dialog.show(fragmentManager, "")
        }
        entities.add(fontEntity)

        val refreshEntity = BottomItemEntity("刷新", R.drawable.bottom_icon_refresh) {
            wv_web.reload()
        }
        entities.add(refreshEntity)

        val warningEntity = BottomItemEntity("投诉", R.drawable.bottom_icon_warning) {

        }
        entities.add(warningEntity)

        return entities
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
