package com.ddu.ui.fragment.person

import com.ddu.R
import com.ddu.icore.common.versionName
import com.ddu.icore.ui.fragment.DefaultFragment
import kotlinx.android.synthetic.main.fragment_setting.*
import org.jetbrains.anko.support.v4.ctx

/**
 * Created by yzbzz on 16/4/11.
 */
class SettingFragment : DefaultFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_setting
    }

    override fun initView() {
        setDefaultTitle("设置")
        oiv_version.setRightText(ctx.versionName)
    }

}
