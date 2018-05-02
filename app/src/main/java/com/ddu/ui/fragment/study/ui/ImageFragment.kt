package com.ddu.ui.fragment.study.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.icore.util.sys.ViewUtils
import com.iannotation.ContentType
import kotlinx.android.synthetic.main.fragment_ui_image.*

/**
 * Created by lhz on 16/5/6.
 */
@ContentType("UI")
class ImageFragment : DefaultFragment() {

    private var isBigModel = true

    override fun getLayoutId(): Int {
        return R.layout.fragment_ui_image
    }

    override fun initView() {

        setDefaultTitle("Image")
        setRightText("",object : View.OnClickListener{
            override fun onClick(v: View?) {
                titleBar?.rightText?.text = if (isBigModel) "小图" else "大图"
                setImg(isBigModel)
                isBigModel = !isBigModel
            }
        })
    }

    private fun setImg(isBigModel: Boolean) {
        val resId = if (isBigModel) R.drawable.guide_hand else R.drawable.guide_hand
        for (i in 1..9) {
            val imageView = ViewUtils.findViewById<ImageView>(mView, getResId("iv" + i, R.id::class.java))
            imageView.setImageResource(resId)
        }
        iv_common.setImageResource(resId)
    }

    companion object {

        fun newInstance(taskId: String): ImageFragment {
            val arguments = Bundle()
            arguments.putString(DefaultFragment.ARGUMENT_TASK_ID, taskId)
            val fragment = ImageFragment()
            fragment.arguments = arguments
            return fragment
        }

        fun getResId(variableName: String, c: Class<*>): Int {
            try {
                val idField = c.getDeclaredField(variableName)
                return idField.getInt(idField)
            } catch (e: Exception) {
                e.printStackTrace()
                return -1
            }

        }
    }

}
