package com.ddu.ui.fragment.person

import com.ddu.R
import android.os.Build
import com.ddu.icore.ui.fragment.DefaultFragment
import kotlinx.android.synthetic.main.fragment_me_phone_info.*

/**
 * Created by yzbzz on 2018/4/16.
 */
class PhoneInfoFragment : DefaultFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_me_phone_info

    override fun initView() {
        val sb = StringBuilder()
        sb.append("device: " + Build.DEVICE +"\n")
        sb.append("mode: " + Build.MODEL +"\n")
        sb.append("version_codeName: " + Build.VERSION.CODENAME +"\n")
        sb.append("version_release: " + Build.VERSION.RELEASE +"\n")
        sb.append("product: " + Build.PRODUCT +"\n")
        tv_phone_info.text = sb.toString()
    }
}