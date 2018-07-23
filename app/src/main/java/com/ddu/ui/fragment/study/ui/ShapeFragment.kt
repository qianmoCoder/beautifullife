package com.ddu.ui.fragment.study.ui

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.ui.dialog.ColorPickerDialog
import com.iannotation.Element
import kotlinx.android.synthetic.main.fragment_ui_shape.*

/**
 * Created by yzbzz on 16/4/14.
 */
@Element("UI")
class ShapeFragment : DefaultFragment(), View.OnClickListener {

    private var beginColor: Int = 0
    private var middleColor: Int = 0
    private var endColor: Int = 0
    private var strokeColor: Int = 0

    private var strokeWidth: Int = 0
    private var roundRadius: Int = 0

    override fun initData(savedInstanceState: Bundle?) {
        strokeColor = resources.getColor(R.color.c_2e3135)
        beginColor = resources.getColor(R.color.c_252525)
        middleColor = resources.getColor(R.color.c_3e7492)
        endColor = resources.getColor(R.color.c_a6c0cd)

    }

    //    private void shape() {
    //        int strokeWidth = 5; // 3dp 边框宽度
    //        int roundRadius = 15; // 8dp 圆角半径
    //        int strokeColor = Color.parseColor("#2E3135");//边框颜色
    //        int fillColor = Color.parseColor("#DFDFE0");//内部填充颜色
    //
    //        GradientDrawable gd = new GradientDrawable();//创建drawable
    //        gd.setColor(fillColor);
    //        gd.setCornerRadius(roundRadius);
    //        gd.set_stroke(strokeWidth, strokeColor);
    //        btnShow.setBackground(gd);
    //
    //        int colors[] = {0xff255779, 0xff3e7492, 0xffa6c0cd};//分别为开始颜色，中间夜色，结束颜色
    //
    //        GradientDrawable gd1 = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
    //
    //    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_ui_shape
    }

    override fun initView() {
        btn_stroke_color.setOnClickListener(this)
        btn_begin_color.setOnClickListener(this)
        btn_middle_color.setOnClickListener(this)
        btn_end_color.setOnClickListener(this)
        btn_style.setOnClickListener(this)
    }

    override fun isShowTitleBar(): Boolean {
        return false
    }

    override fun onClick(view: View) {
        val viewId = view.id
        if (viewId == R.id.btn_style) {
            setBackground()
        } else {
            showColorDialog(view)
        }
    }

    private fun setBackground() {
        roundRadius = getValueFromEditText(et_round_radius!!)
        strokeWidth = getValueFromEditText(et_stroke!!)
        val colors = intArrayOf(beginColor, middleColor, endColor)
        val gd = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors)//创建drawable
        gd.cornerRadius = roundRadius.toFloat()
        gd.setStroke(strokeWidth, strokeColor)
        gd.gradientRadius = 3f
        btn_style!!.background = gd
    }

    private fun showColorDialog(view: View) {
        val colorPickerDialog = ColorPickerDialog(mContext, ColorPickerDialog.OnColorChangedListener { color ->
            val id = view.id
            view.setBackgroundColor(color)
            if (id == R.id.btn_begin_color) {
                beginColor = color
            } else if (id == R.id.btn_middle_color) {
                middleColor = color
            } else if (id == R.id.btn_end_color) {
                endColor = color
            } else if (id == R.id.btn_stroke_color) {
                strokeColor = color
            }
        }, Color.RED)
        colorPickerDialog.show()
    }

    private fun getValueFromEditText(editText: EditText): Int {
        val text = editText.text.toString().trim { it <= ' ' }
        var result: Int
        try {
            result = Integer.parseInt(text)
        } catch (e: Exception) {
            result = 1
        }

        return result
    }

    companion object {

        fun newInstance(taskId: String): ShapeFragment {
            val arguments = Bundle()
            arguments.putString(DefaultFragment.ARGUMENT_TASK_ID, taskId)
            val fragment = ShapeFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
}
