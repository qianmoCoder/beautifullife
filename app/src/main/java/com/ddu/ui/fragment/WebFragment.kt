package com.ddu.ui.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.webkit.*
import com.ddu.R
import com.ddu.icore.common.act
import com.ddu.icore.common.clipText
import com.ddu.icore.common.ctx
import com.ddu.icore.common.startBrowser
import com.ddu.icore.dialog.BottomDialogFragment
import com.ddu.icore.dialog.DefaultGridBottomDialogFragment
import com.ddu.icore.entity.BottomItemEntity
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.icore.ui.widget.CustomerBottomBar
import com.ddu.util.ToastUtils
import kotlinx.android.synthetic.main.fragment_web.*

/**
 * Created by yzbzz on 16/4/6.
 */
class WebFragment : DefaultFragment() {

    private var mWebSettings: WebSettings? = null
    private var mTitle = ""
    private var mUrl = ""

    override fun initData(savedInstanceState: Bundle?) {
        arguments?.apply {
            mTitle = getString("title", "")
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
//        wv_web.setFindListener { activeMatchOrdinal, numberOfMatches, isDoneCounting ->
//            Log.v("lhz", "$activeMatchOrdinal - $numberOfMatches - $isDoneCounting")
//        }

        initTitle()

        wv_web.loadUrl(mUrl)
        wv_web.setDownloadListener(object : DownloadListener{
            override fun onDownloadStart(url: String?, userAgent: String?, contentDisposition: String?, mimetype: String?, contentLength: Long) {
                Log.v("lhz","download url: " + url)
                val intent = Intent(Intent.ACTION_VIEW)
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                intent.setData(Uri.parse(url))
                startActivity(intent);
            }

        })
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
        val dialog = DefaultGridBottomDialogFragment.newInstance(list = entity, cb = { _, _, dialog ->
            dialog.dismissAllowingStateLoss()
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

        val searchEntity = BottomItemEntity("搜索页面内容", R.drawable.bottom_icon_search) {
            //            wv_web.showFindDialog("一", true)
//            wv_web.findAllAsync("一")
//            wv_web.setFindListener { activeMatchOrdinal, numberOfMatches, isDoneCounting ->
//                ToastUtils.showToast("$activeMatchOrdinal - $numberOfMatches - $isDoneCounting")
//                Log.v("lhz", "$activeMatchOrdinal - $numberOfMatches - $isDoneCounting")
//            }
            CustomerBottomBar.make(wv_web).show()
        }
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
            val entities = mutableListOf<BottomItemEntity>()
            val smallestEntity = BottomItemEntity("小", data = "50")
            val smallerEntity = BottomItemEntity("较小", data = "75")
            val normalEntity = BottomItemEntity("标准", data = "100")
            val largerEntity = BottomItemEntity("大", data = "150")
            val largestEntity = BottomItemEntity("较大", data = "200")
            entities.add(smallestEntity)
            entities.add(smallerEntity)
            entities.add(normalEntity)
            entities.add(largerEntity)
            entities.add(largestEntity)

//            val dialog = DefaultLinearBottomDialogFragment.newInstance(list = entities, cb = { entity, _, dialog ->
//                dialog.dismissAllowingStateLoss()
//                mWebSettings?.textZoom = entity?.data?.toInt() ?: 100
//            })
//            dialog.show(fragmentManager, "")

            val dialog = BottomDialogFragment.Builder()
                    .create()
            dialog.showDialog(act)
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
            Log.v("lhz","shouldOverrideUrlLoading: " + url)
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
            args.putString("title", title)
            args.putString("url", url)
            fragment.arguments = args
            return fragment
        }
    }
}
