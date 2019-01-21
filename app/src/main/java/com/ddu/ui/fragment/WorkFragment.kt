package com.ddu.ui.fragment

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.Gravity
import com.ddu.R
import com.ddu.icore.callback.Consumer1
import com.ddu.icore.dialog.AlertDialogFragment
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.icore.util.HtmlUtils
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
            val clickableHtml = HtmlUtils.getClickableHtml(
                    getString(R.string.home_service_fee_text),
                    object : Consumer1<String> {
                        override fun accept(t: String) {
                            val args = Bundle()
                            args.putString("url", t)
                            startFragment(WebFragment::class.java, args)
                        }
                    }
            )

            val dialog = AlertDialogFragment().apply {
                title = "即将前往"
                special = clickableHtml
                msgGravity = Gravity.LEFT
                leftText = "不同意"
                rightText = "同意"
                mLeftClickListener = { _, _ ->
                    dismissAllowingStateLoss()
                }
                mRightClickListener = { _, _ ->
                    dismissAllowingStateLoss()
                }
            }
            dialog.show(fragmentManager, "")

            tv_html.setText(clickableHtml)
            tv_html.movementMethod = LinkMovementMethod()
        }

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
