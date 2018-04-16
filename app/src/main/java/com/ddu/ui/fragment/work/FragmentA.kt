package com.ddu.ui.fragment.work

import android.net.Uri
import android.os.Bundle
import com.ddu.R
import com.ddu.icore.common.putPreference
import com.ddu.icore.ui.fragment.DefaultFragment
import kotlinx.android.synthetic.main.fragment_work_state.*
import org.jetbrains.anko.support.v4.ctx
import java.net.URLEncoder

/**
 * Created by lhz on 16/4/6.
 */
class FragmentA : DefaultFragment() {

    var schemeUrl: String? = null
    var url: String? = null


    override fun initData(savedInstanceState: Bundle?) {
        url = URLEncoder.encode("https://www.baidu.com", "utf-8")
        schemeUrl = "etcp://1?url=$url&type=1"
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_work_state
    }

    override fun initView() {
        tv_show.text = schemeUrl
        button.setOnClickListener {
            //                ShareDialogFragmentT bottomSheetDialogFragment = new ShareDialogFragmentT();
            //                bottomSheetDialogFragment.show(getFragmentManager(), "");
            //                replaceFragment(FragmentB.newInstance());
//            (mActivity as ShowDetailActivity).replaceFragment(FragmentB.newInstance(), FragmentUtils.FRAGMENT_ADD_TO_BACK_STACK)
            ctx.putPreference("defaultValue", "hello")
        }
        btn_ok.setOnClickListener {
            //            val f = FragmentB.newInstance()
            //                replaceFragment(f);
//            item_time_line_mark.count = 2
            val uri = Uri.parse(schemeUrl)
            val sb = StringBuilder()
//            sb.append(uri.toString() + " - ")
//            sb.append(uri.path + "path:  " + " - ")
            sb.append(uri.scheme + " ")
            val names = uri.queryParameterNames
            for (name in names) {
                val p = uri.getQueryParameter(name)
                sb.append("$name - $p")
            }
//            sb.append(uri.query + " ")
//            sb.append(uri.getQueryParameter("isFeedBack") + " ")
//            sb.append(uri.getQueryParameter("synId") + " ")

//            setDefaultTitle("FragmentA")
//            val set = setOf("1", "2", "3")
//            val type = set::class.java.genericSuperclass as? ParameterizedType
//            sb.append(type?.actualTypeArguments!![0]::class.java.name)
//            GenericTypeResolver
            tv_show.text = sb.toString()
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
}
