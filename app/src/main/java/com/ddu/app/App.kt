package com.ddu.app

import android.content.IntentFilter
import android.content.res.Configuration
import android.content.res.Resources
import android.net.wifi.WifiManager
import com.ddu.db.AppDatabase
import com.ddu.receiver.NetInfoBroadcastReceiver
import com.ddu.routes.ElementProvider
import com.ddu.routes.RouterProvider
import com.ddu.util.SystemUtils
import com.ddu.util.xml.PullParserUtils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.DiskLogAdapter
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader

/**
 * Created by yzbzz on 16/4/6.
 */
class App : BaseApp() {


    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            //                layout.setPrimaryColorsId(R.color.c_868686, android.R.color.white)
            ClassicsHeader(context).apply {
                setEnableLastTime(false)
            }
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            ClassicsFooter(
                context
            ).setDrawableSize(20f)
        }
    }

    override fun onCreate() {
        super.onCreate()
        init()
        Logger.addLogAdapter(AndroidLogAdapter())
        Logger.addLogAdapter(DiskLogAdapter())
    }

    private fun init() {
        val processName = SystemUtils.getProcessName()
        val currentPackageName = packageName
        if (processName == currentPackageName) {// 防止多进程重复实始化
            registerActivityLifecycleCallbacks(this)
            initData()
            registorNetInfoBroadcastReceiver()
        }
    }


    private fun registorNetInfoBroadcastReceiver() {
        val filter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        this.registerReceiver(NetInfoBroadcastReceiver(), filter)
    }

    private fun initData() {
        val studyContentBox = AppDatabase.getInstance(this).studyContentDao().getStudyContents()
        val count = studyContentBox.size
        if (count <= 0) {
            loadFile()
        }
    }

    private fun loadFile() {
        try {
            val studyContents = PullParserUtils.getStudyContent(assets.open("config_tag.xml"))
            AppDatabase.getInstance(this).studyContentDao().insertAll(studyContents)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    // 强制字体不随着系统改变而改变
    override fun onConfigurationChanged(newConfig: Configuration) {
        if (newConfig.fontScale != 1f) {
            resources
        }
        super.onConfigurationChanged(newConfig)
    }

    override fun getResources(): Resources {
        var res = super.getResources()
        if (res.configuration.fontScale != 1f) {//非默认值
            val newConfig = Configuration()
            newConfig.setToDefaults()
            //设置默认
            //            res.updateConfiguration(newConfig, res.getDisplayMetrics());
            val context = createConfigurationContext(newConfig)
            res = context.resources
        }
        return res
    }

    companion object {

        var routerProvider = RouterProvider()

        var elementProvider = ElementProvider()
    }
}
