package com.ddu.ui.fragment.study.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.icore.ui.help.ShapeInjectHelper
import com.iannotation.Element
import kotlinx.android.synthetic.main.fragment_ui_shape_advanced.*

/**
 * Created by yzbzz on 16/4/14.
 */
@Element("UI")
class ShapeAdvancedFragment : DefaultFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_ui_shape_advanced
    }

    override fun initView() {
        btn_start.setOnClickListener {
            ll_items.removeAllViews()
            val count = Integer.parseInt(et_count.text.toString())
            val resId = R.layout.fragment_ui_common_textview
            for (i in 0 until count) {
                val linearLayout = layoutInflater.inflate(resId, null) as TextView
                val shapeInjectHelper = ShapeInjectHelper(linearLayout)
                if (i == 0) {
                    shapeInjectHelper.shapeType(ShapeInjectHelper.SEGMENT)
                    shapeInjectHelper.shapeDirection(ShapeInjectHelper.DIRECTION_TOP)
                    shapeInjectHelper.radius(5f)
                }

                if (i == count - 1) {
                    shapeInjectHelper.shapeType(ShapeInjectHelper.SEGMENT)
                    shapeInjectHelper.shapeDirection(ShapeInjectHelper.DIRECTION_BOTTOM)
                    shapeInjectHelper.radius(5f)
                }
                shapeInjectHelper.setBackground()
                linearLayout.setOnClickListener(object : View.OnClickListener {

                    var isCheck = true

                    override fun onClick(v: View) {
                        if (isCheck) {
                            linearLayout.setBackgroundColor(Color.RED)
                        } else {
                            linearLayout.setBackgroundColor(Color.WHITE)
                        }
                        isCheck = !isCheck
                    }
                })
                ll_items.addView(linearLayout)
            }
        }
    }

    companion object {

        fun newInstance(taskId: String): ShapeAdvancedFragment {
            val arguments = Bundle()
            arguments.putString(DefaultFragment.ARGUMENT_TASK_ID, taskId)
            val fragment = ShapeAdvancedFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

}
