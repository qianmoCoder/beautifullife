package com.ddu.ui.fragment.work.kotlin

import android.os.Bundle
import android.view.View
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.ui.fragment.WebFragment
import com.iannotation.ICodeLabsElement

/**
 * Created by yzbzz on 2018/6/8.
 */
@ICodeLabsElement(path = "Kotlin_CodeLabs", parentId = "1", parentContent = "Notifications",
        id = "1", content = "Using Android Notifications")
class NotificationFragment : DefaultFragment() {

    override fun getLayoutId(): Int {
        return R.layout.study_customer_dial_view
    }

    override fun initView() {
        setDefaultTitle("Notification")
        setRightText("地址", View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("title", "Using Android Notifications")
            bundle.putString("url", "https://codelabs.developers.google.com/codelabs/advanced-android-kotlin-training-notifications/#0")
            startFragment(WebFragment::class.java, bundle)
        })
    }


}
