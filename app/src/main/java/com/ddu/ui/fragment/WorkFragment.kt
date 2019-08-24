package com.ddu.ui.fragment

import android.graphics.Color
import android.os.Bundle
import com.ddu.R
import com.ddu.icore.common.adapters.bindTextColor
import com.ddu.icore.ui.fragment.DefaultFragment
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

        oiv_program_swift.setOnClickListener {
        }

        btn_get.setOnClickListener {
            //            SophixManager.getInstance().queryAndLoadNewPatch()
            tv_html.isEnabled = false
        }

        tv_html.text = "LIMILIMILIMILIMILIMILIMILIMILIMILIMILIMILIMI"
        tv_html.isClickable = true
        tv_html.isFocusable = true
//        tv_html.setTextColor( resources.getColorStateList(R.color.fragment_ui_navi_item_text_selector))
        tv_html.bindTextColor(
            resources.getColor(R.color.c_ff0000),
            resources.getColor(R.color.c_4897fa),
            Color.BLACK
        )

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
