package com.ddu.databinding

import android.databinding.BindingAdapter
import android.graphics.drawable.GradientDrawable
import android.support.annotation.ColorInt
import android.view.View
import com.ddu.icore.common.parseColor

/**
 * Created by yzbzz on 2018/9/12.
 */
@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter(value = ["bg_radius", "bg_color", "bg_color_s"], requireAll = false)
fun bindBackground(v: View, radius: Int, @ColorInt color: Int?, colorString: String?) {
    val gd = GradientDrawable()
    if (null != colorString) {
        gd.setColor(colorString.parseColor())
    } else if (null != color) {
        gd.setColor(color)
    }
    gd.cornerRadius = radius.toFloat()
    v.background = gd
}