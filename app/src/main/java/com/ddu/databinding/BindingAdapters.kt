package com.ddu.databinding

import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import com.ddu.icore.common.ext.parseColor


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
fun bindBackground(v: View, radius: Float, @ColorInt color: Int?, colorString: String?) {
    val gd = GradientDrawable()


    if (null != color) {
        gd.setColor(color)
    } else if (null != colorString) {
        gd.setColor(colorString.parseColor())
    }

    gd.cornerRadius = radius

    v.background = gd
}