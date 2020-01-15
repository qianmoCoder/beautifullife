package com.ddu.ui.fragment.work.kotlin

import android.os.Bundle
import android.view.View
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.ui.fragment.WebFragment
import com.iannotation.ICodeLabsElement
import kotlinx.android.synthetic.main.study_customer_my_canvas_view.*

/**
 * Created by yzbzz on 2018/6/8.
 */
@ICodeLabsElement(path = "Kotlin_CodeLabs", parentId = "2", parentContent = "Advanced Graphics",
        id = "2", content = "Drawing on Canvas Objects")
class MyCanvasViewFragment : DefaultFragment() {


    override fun getLayoutId(): Int {
        return R.layout.study_customer_my_canvas_view
    }

    override fun initView() {
        myCanvasView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        myCanvasView.contentDescription = getString(R.string.canvasContentDescription)
        setDefaultTitle("MyCanvasView")
        setRightText("地址", View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("title", "Drawing on Canvas Objects")
            bundle.putString("url", "https://codelabs.developers.google.com/codelabs/advanced-android-kotlin-training-canvas/#0")
            startFragment(WebFragment::class.java, bundle)
        })
    }


}
