package com.ddu.ui.fragment.study.ui

import android.util.Log
import android.view.View
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_study_kotlin.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.doAsync
import kotlin.concurrent.thread

/**
 * Created by yzbzz on 2018/3/8.
 */
@IElement("UI")
class KotlinFragment : DefaultFragment(), View.OnClickListener {

    private var sb = StringBuilder()

    override fun getLayoutId(): Int {
        return R.layout.fragment_study_kotlin
    }

    override fun initView() {
        btn_start.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_start -> {
                sb = StringBuilder()
                test()
                tv_show_info.text = sb.toString()
//                Log.v("lhz", sb.toString())
            }

        }
    }

    private fun test() {
        launchLog("main")
        asyncLog("main")
        doAsyncLog("main")
        thread {
            sb.append("thread")
            launchLog("thread")
            asyncLog("thread")
            doAsyncLog("thread")
        }

    }

    private fun launchLog(threadName: String) {
        launch {
            val t = "$threadName-launch-default-${Thread.currentThread().id}\n"
            sb.append(t)
            Log.v("lhzz", t)
        }

        launch(UI) {
            val t = "$threadName-launch-ui-${Thread.currentThread().id}\n"
            sb.append(t)
            Log.v("lhzz", t)
        }

        launch(CommonPool) {
            val t = "$threadName-launch-common-${Thread.currentThread().id}\n"
            sb.append(t)
            Log.v("lhzz", t)
        }
    }

    private fun asyncLog(threadName: String) {
        async {
            val t = "$threadName-async-default-${Thread.currentThread().id}\n"
            sb.append(t)
            Log.v("lhzz", t)
        }

        async(UI) {
            val t = "$threadName-async-ui-${Thread.currentThread().id}\n"
            sb.append(t)
            Log.v("lhzz", t)
        }

        async(CommonPool) {
            val t = "$threadName-async-common-${Thread.currentThread().id}\n"
            sb.append(t)
            Log.v("lhzz", t)
        }
    }

    private fun doAsyncLog(threadName: String) {
        doAsync {
            val t = "$threadName-doAsync-default-${Thread.currentThread().id}\n"
            sb.append(t)
            Log.v("lhzz", t)
        }
    }
}
