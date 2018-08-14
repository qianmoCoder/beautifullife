package com.ddu.ui.fragment.study.ui

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.icore.util.sys.ViewUtils
import com.ddu.ui.dialog.ColorPickerDialog
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_ui_shape.*
import org.jetbrains.anko.backgroundResource

/**
 * Created by yzbzz on 16/4/14.
 */
@IElement("UI")
class ShapeFragment : DefaultFragment(), RadioGroup.OnCheckedChangeListener, View.OnClickListener {


    private var beginColor: Int = 0
    private var middleColor: Int = 0
    private var endColor: Int = 0

    private var strokeWidth: Int = 0
    private var strokeColor: Int = 0

    private var roundRadius: Int = 0
    private var angleRadius: Float = 0f

    override fun initData(savedInstanceState: Bundle?) {
        strokeColor = resources.getColor(R.color.c_2e3135)
        beginColor = resources.getColor(R.color.c_252525)
        middleColor = resources.getColor(R.color.c_3e7492)
        endColor = resources.getColor(R.color.c_a6c0cd)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_ui_shape
    }

    override fun initView() {
        setDefaultTitle("Shape")
        rg_xml_shape.setOnCheckedChangeListener(this)
        rg_xml_shape.check(R.id.rb_blue)

        btn_stroke_color.setOnClickListener(this)
        btn_begin_color.setOnClickListener(this)
        btn_middle_color.setOnClickListener(this)
        btn_end_color.setOnClickListener(this)
        btn_style.setOnClickListener(this)
    }

    override fun onCheckedChanged(p0: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.rb_blue -> setBtnBackground("blue")
            R.id.rb_green -> setBtnBackground("green")
            R.id.rb_red -> setBtnBackground("red")
            R.id.rb_yellow -> setBtnBackground("yellow")
        }
    }

    fun setBtnBackground(colorText: String) {
        val resId = ViewUtils.getResId("shape_view_$colorText", R.drawable::class.java)
        val hollowResId = ViewUtils.getResId("shape_view_${colorText}_hollow", R.drawable::class.java)
        btn_xml_shape.backgroundResource = resId
        btn_xml_hollow_shape.backgroundResource = hollowResId
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
        strokeWidth = getValueFromEditText(et_stroke)

        roundRadius = getValueFromEditText(et_round_radius)
        angleRadius = getValueFromEditText(et_angle).toFloat()

        val colors = intArrayOf(beginColor, middleColor, endColor)
        val gd = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors)//创建drawable
        gd.cornerRadius = roundRadius.toFloat()
        gd.setStroke(strokeWidth, strokeColor)
        gd.gradientRadius = angleRadius
        btn_style.background = gd
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
