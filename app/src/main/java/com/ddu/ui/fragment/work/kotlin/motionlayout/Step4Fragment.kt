package com.ddu.ui.fragment.work.kotlin.motionlayout

import android.os.Bundle
import android.view.View
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.ui.fragment.WebFragment
import com.iannotation.ICodeLabsElement


/**
 * Created by yzbzz on 16/4/8.
 */
@ICodeLabsElement(path = "Kotlin_CodeLabs", parentId = "3", parentContent = "Animation",
        id = "2", content = "Animation with MotionLayout")
class Step4Fragment : DefaultFragment() {

    override fun getLayoutId(): Int {
        return R.layout.study_motion_layout
    }

    override fun initView() {
        setDefaultTitle("MotionLayoutFragment")
        setRightText("地址", View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("title", "Animation with MotionLayout")
            bundle.putString("url", "https://codelabs.developers.google.com/codelabs/motion-layout/#0")
            startFragment(WebFragment::class.java, bundle)
        })
    }
}
