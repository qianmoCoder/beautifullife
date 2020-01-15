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
@ICodeLabsElement(path = "Kotlin_CodeLabs", parentId = "2", parentContent = "Advanced Graphics",
        id = "3", content = "Clipping Canvas Objects")
class ClippingFragment : DefaultFragment() {

    override fun getLayoutId(): Int {
        return R.layout.study_customer_clipped_view
    }

    override fun initView() {
        setDefaultTitle("ClippedView")
        setRightText("地址", View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("title", "Clipping Canvas Objects")
            bundle.putString("url", "https://codelabs.developers.google.com/codelabs/advanced-android-kotlin-training-clipping-canvas-objects/#0")
            startFragment(WebFragment::class.java, bundle)
        })
    }


}
