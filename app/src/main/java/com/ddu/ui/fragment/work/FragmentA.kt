package com.ddu.ui.fragment.work

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.ddu.R
import com.ddu.icore.common.ext.ctx
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.ui.activity.TestActivity
import com.ddu.util.ToastUtils
import kotlinx.android.synthetic.main.fragment_work_state.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.net.URLEncoder

/**
 * Created by lhz on 16/4/6.
 */
class FragmentA : DefaultFragment() {

    private var schemeUrl: String? = null
        set(value) {
            startTo(value)
        }
    var yzbzzUrl: String? = null
    var httpUrl: String? = null
    var defaultData = "yzbzz://5"
    var login = "1"

    override fun initData(savedInstanceState: Bundle?) {
        yzbzzUrl = URLEncoder.encode("yzbzz://5?userId=3&phone=186xxx&t=张三", "utf-8")
        httpUrl = URLEncoder.encode("http://www.baidu.com?userId=3&phone=186xxx&t=张三", "utf-8")
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_work_state
    }

    override fun initView() {
        btn_ok.setOnClickListener {
            if (login.equals("1", ignoreCase = true)) {
                login = "0"
                ToastUtils.showToast("登录关闭")
            } else {
                login = "1"
                ToastUtils.showToast("登录开启")
            }
        }
        btn_yzbzz.setOnClickListener {
            schemeUrl = "yzbzz://3?login=$login"
        }
        btn_http.setOnClickListener {
            schemeUrl = "http://www.baidu.com"
        }
        btn_yzbzz_s_e.setOnClickListener {
            schemeUrl = "yzbzz://100?url=$yzbzzUrl&login=$login"
        }
        btn_yzbzz_s_h.setOnClickListener {
            schemeUrl = "yzbzz://100?url=$httpUrl&login=$login"
        }
        btn_yzbzz_s_d_e.setOnClickListener {
            defaultData = "yzbzz://2"
            schemeUrl = "yzbzz://101?login=$login"
        }
        btn_yzbzz_s_d_h.setOnClickListener {
            defaultData = "http://www.163.com"
            schemeUrl = "yzbzz://101?login=$login"
        }
        btn_yzbzz_s_d_error.setOnClickListener {
            defaultData = "yzbzz://102"
            schemeUrl = "yzbzz://101?login=$login"
        }

        btn_yzbzz_s_d_error_o.setOnClickListener {
            defaultData = "abc**102"
            schemeUrl = "yzbzz://101?login=$login"
        }

    }

    fun startTo(url: String?) {
        val intent = Intent(ctx, TestActivity::class.java)
        val uri = Uri.parse(url)
        intent.data = uri
        intent.putExtra("defaultData", defaultData)
        startActivity(intent)
    }

    companion object {

        fun newInstance(): FragmentA {
            val fragment = FragmentA()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    fun appendUrl(url: String?, urlParams: Map<String, String>?): String {
        if (null == url) {
            return ""
        }
        if (urlParams == null || urlParams.size <= 0) {
            return url
        }
        val httpUrl = url!!.toHttpUrlOrNull() ?: return url
        val urlBuilder = httpUrl.newBuilder()
        for ((key, value) in urlParams) {
            urlBuilder.addQueryParameter(key, value)
        }
        return urlBuilder.build().toString()
    }
}
