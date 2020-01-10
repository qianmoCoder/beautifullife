package com.ddu.ui.fragment

import android.graphics.Color
import android.os.Bundle
import com.ddu.R
import com.ddu.icore.common.ext.startActivity
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.ui.activity.service.MyClientActivity
import com.ddu.ui.fragment.work.kotlin.CodeLabsFragment
import kotlinx.android.synthetic.main.fragment_work.*

/**
 * Created by yzbzz on 16/4/6.
 */
class WorkFragment : DefaultFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_work
    }

    override fun initView() {
        setTitle(R.string.main_tab_work)

        oiv_android_ipc.enableDefaultLeftText(Color.parseColor("#b27BB641"))
        oiv_android_ipc.setOnClickListener {
            startActivity<MyClientActivity>()
        }
        oiv_android_service.enableDefaultLeftText(Color.parseColor("#b27BB641"))

        oiv_kotlin_code_labs.enableDefaultLeftText(Color.parseColor("#b23A64DC"))
        oiv_kotlin_code_labs.setOnClickListener {
            startFragment(CodeLabsFragment::class.java)
        }

        oiv_jet_pack.enableDefaultLeftText(Color.parseColor("#b2FED133"))

        oiv_java.enableDefaultLeftText(Color.parseColor("#b2FB303A"))

    }

    fun ToDBC(input: String): String {
        val c = input.toCharArray()
        for (i in c.indices) {
            if (c[i] == '\u3000') {
                continue
            }
            if (c[i].toInt() == 12288) {
                c[i] = 32.toChar()
                continue
            }
            if (c[i].toInt() > 65280 && c[i].toInt() < 65375)
                c[i] = (c[i].toInt() - 65248).toChar()
        }
        return String(c)
    }

    fun ToDBC1(input: String): String {
        // 导致TextView异常换行的原因：安卓默认数字、字母不能为第一行以后每行的开头字符，因为数字、字母为半角字符
        // 所以我们只需要将半角字符转换为全角字符即可
        val c = input.toCharArray()
        for (i in c.indices) {
            if (c[i] == ' ') {
                c[i] = '\u3000'
            } else if (c[i] < '\u007f') {
                c[i] = (c[i].toInt() + 65248).toChar()
            }
        }
        return String(c)
    }


    companion object {

        fun newInstance(): WorkFragment {
            val fragment = WorkFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
