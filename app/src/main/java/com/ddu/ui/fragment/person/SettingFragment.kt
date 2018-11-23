package com.ddu.ui.fragment.person

import android.content.res.Configuration
import android.support.v7.app.AppCompatDelegate
import com.ddu.R
import com.ddu.icore.common.*
import com.ddu.icore.ui.fragment.DefaultFragment
import kotlinx.android.synthetic.main.fragment_setting.*


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

        val isNightMode = ctx.findPreference("isNightMode", false) ?: false
        st_night.isChecked = isNightMode
        st_night.setOnCheckedChangeListener({ _, isChecked ->
            setMode(isChecked)
        })

        val isAutoNight = ctx.findPreference("isAutoNight", false) ?: false
        st_night_auto.isChecked = isAutoNight
        st_night_auto.setOnCheckedChangeListener { _, isChecked ->
            ctx.putPreference("isAutoNight", isChecked)
        }
    }

    /***
     * mode == Configuration.UI_MODE_NIGHT_NO  mode == Configuration.UI_MODE_NIGHT_YES
     * MODE_NIGHT_NO 使用亮色(light)主题
     * MODE_NIGHT_YES 使用暗色(dark)主题
     * MODE_NIGHT_AUTO 根据当前时间自动切换亮色/暗色
     * MODE_NIGHT_FOLLOW_SYSTEM(默认选项).设置为跟随系统，通常为MODE_NIGHT_NO
     */
    private fun setMode(isChecked: Boolean) {
        val mode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            ctx.putPreference("isNightMode", true)
        } else {
            // AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(mode)
            ctx.putPreference("isNightMode", false)
        }
        act.recreate()
    }

}
