package com.ddu.app

import android.content.Context
import android.content.IntentFilter
import android.content.res.Configuration
import android.content.res.Resources
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatDelegate
import com.ddu.R
import com.ddu.db.entity.MyObjectBox
import com.ddu.db.entity.StudyContent
import com.ddu.icore.common.ext.findPreference
import com.ddu.receiver.NetInfoBroadcastReceiver
import com.ddu.routes.ElementProvider
import com.ddu.routes.RouterProvider
import com.ddu.util.SystemUtils
import com.ddu.util.xml.PullParserUtils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.DiskLogAdapter
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.*
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import io.objectbox.Box
import io.objectbox.BoxStore
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

/**
 * Created by yzbzz on 16/4/6.
 */
class App : BaseApp(),KodeinAware {

    override val kodein = Kodein {
        bind<Context>() with singleton {this@App}
        import(androidXModule(this@App))
    }

    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(object : DefaultRefreshHeaderCreator {
            override fun createRefreshHeader(
                context: Context,
                layout: RefreshLayout
            ): RefreshHeader {
//                layout.setPrimaryColorsId(R.color.c_868686, android.R.color.white)
                return ClassicsHeader(context).apply {
                    setEnableLastTime(false)
                }
            }
        })
        SmartRefreshLayout.setDefaultRefreshFooterCreator(object : DefaultRefreshFooterCreator {
            override fun createRefreshFooter(
                context: Context,
                layout: RefreshLayout
            ): RefreshFooter {
                return ClassicsFooter(context).setDrawableSize(20f)
            }
        })
    }

    override fun onCreate() {
        super.onCreate()
        init()
        Logger.addLogAdapter(AndroidLogAdapter())
        Logger.addLogAdapter(DiskLogAdapter())
    }

    private fun init() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return
//        }
//        LeakCanary.install(this)
        val processName = SystemUtils.getProcessName()
        val currentPackageName = packageName
        if (processName == currentPackageName) {// 防止多进程重复实始化
            setupDatabase()
            registerActivityLifecycleCallbacks(this)
            initData()
            registorNetInfoBroadcastReceiver()
        }
    }

    private fun initNightMode() {
        val isAutoNight = findPreference("isAutoNight", false) ?: false
        if (isAutoNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO)
        } else {
            val isNightMode = findPreference("isNightMode", false) ?: false
            if (isNightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun registorNetInfoBroadcastReceiver() {
        val filter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        this.registerReceiver(NetInfoBroadcastReceiver(), filter)
    }

    private fun initData() {
        val studyContentBox = boxStore!!.boxFor(StudyContent::class.java)
        val count = studyContentBox.count()
        if (count <= 0) {
            try {
                loadFile(studyContentBox)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun loadFile(studyContentBox: Box<StudyContent>) {
        try {
            val studyContents = PullParserUtils.getStudyContent(assets.open("config_tag.xml"))
            studyContentBox.put(studyContents)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun loadStringArray(studyContentBox: Box<StudyContent>) {
        val titleList = resources.getStringArray(R.array.study_ui_title)
        val descList = resources.getStringArray(R.array.study_ui_description)
        val observableTitle = Observable.fromArray(*titleList)
        val observableDescList = Observable.fromArray(*descList)

        Observable.zip(
            observableTitle,
            observableDescList,
            BiFunction<String, String, StudyContent> { s, s2 ->
                val studyItemModel = StudyContent()
                studyItemModel.setTitle(s)
                studyItemModel.setDescription(s2)
                studyItemModel.setType(s)
                studyItemModel
            }).subscribe { studyContent -> studyContentBox.put(studyContent) }
    }


    private fun setupDatabase() {
        boxStore = MyObjectBox.builder().androidContext(this).build()
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

        var boxStore: BoxStore? = null
            private set

        var routerProvider = RouterProvider()

        var elementProvider = ElementProvider()
    }
}
