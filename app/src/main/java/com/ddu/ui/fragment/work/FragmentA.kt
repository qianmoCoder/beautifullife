package com.ddu.ui.fragment.work

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.ui.activity.TestActivity
import kotlinx.android.synthetic.main.fragment_work_state.*
import okhttp3.HttpUrl
import org.jetbrains.anko.support.v4.ctx
import java.net.URLEncoder

/**
 * Created by lhz on 16/4/6.
 */
class FragmentA : DefaultFragment() {

    private var schemeUrl: String? = null
    var url: String? = null
    val defaultData = "etcp://3"

    override fun initData(savedInstanceState: Bundle?) {
        url = URLEncoder.encode("etcp://5?userId=3&phone=186xxx&t=张三", "utf-8")
        schemeUrl = "etcp://100?url=$url&login=1"
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_work_state
    }

    override fun initView() {

        button.setOnClickListener {
            //                ShareDialogFragmentT bottomSheetDialogFragment = new ShareDialogFragmentT();
            //                bottomSheetDialogFragment.show(getFragmentManager(), "");
            //                replaceFragment(FragmentB.newInstance());
//            (mActivity as ShowDetailActivity).replaceFragment(FragmentB.newInstance(), FragmentUtils.FRAGMENT_ADD_TO_BACK_STACK)
//            ctx.putPreference("defaultValue", "hello")
            val intent = Intent(ctx, TestActivity::class.java)
            val uri = Uri.parse(schemeUrl)
            intent.data = uri
            intent.putExtra("defaultData", defaultData)
            startActivity(intent)
        }
        btn_ok.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("bbc://hello"))
            startActivity(intent)
            //            val f = FragmentB.newInstance()
            //                replaceFragment(f);
//            item_time_line_mark.count = 2
//            val uri = Uri.parse(schemeUrl)
//            val sb = StringBuilder()
////            sb.append(uri.toString() + " - ")
////            sb.append(uri.path + "path:  " + " - ")
//            sb.append(uri.scheme + " \n")
//            sb.append(uri.host + " \n")
//            val names = uri.queryParameterNames
//            val bbc = uri.getQueryParameter("bbc")
//            sb.append("bbc: $bbc")
//            for (name in names) {
//                val p = uri.getQueryParameter(name)
//                sb.append("$name - $p")
//                sb.append("\n")
//            }
//            sb.append(uri.query + " ")
//            sb.append(uri.getQueryParameter("isFeedBack") + " ")
//            sb.append(uri.getQueryParameter("synId") + " ")

//            setDefaultTitle("FragmentA")
//            val set = setOf("1", "2", "3")
//            val type = set::class.java.genericSuperclass as? ParameterizedType
//            sb.append(type?.actualTypeArguments!![0]::class.java.name)
//            GenericTypeResolver
//            tv_show.text = sb.toString()
        }

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
        val httpUrl = HttpUrl.parse(url) ?: return url
        val urlBuilder = httpUrl.newBuilder()
        for ((key, value) in urlParams) {
            urlBuilder.addQueryParameter(key, value)
        }
        return urlBuilder.build().toString()
    }
}
