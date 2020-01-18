package com.ddu.ui.fragment.work.kotlin.notification

import android.os.Bundle
import android.view.View
import com.ddu.R
import com.ddu.icore.ui.activity.BaseActivity
import com.ddu.ui.fragment.WebFragment
import com.ddu.ui.fragment.work.kotlin.notification.ui.EggTimerFragment
import com.iannotation.ICodeLabsElement

/**
 * Created by yzbzz on 2018/6/8.
 */
@ICodeLabsElement(path = "Kotlin_CodeLabs", parentId = "1", parentContent = "Notifications",
        id = "1", content = "Using Android Notifications", classType = "1")
class EggTimerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_egg_timer)
        setDefaultTitle("Notification")
        setRightText("地址", View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("title", "Using Android Notifications")
            bundle.putString("url", "https://codelabs.developers.google.com/codelabs/advanced-android-kotlin-training-notifications/#0")
            startFragment(WebFragment::class.java, bundle)
        })
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, EggTimerFragment.newInstance())
                    .commitNow()
        }
    }
}
