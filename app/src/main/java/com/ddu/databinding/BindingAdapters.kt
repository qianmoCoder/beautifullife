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

//@BindingMethods(
//        BindingMethod(type = android.widget.ImageView::class, attribute = "android:tint", method = "setImageTintList"),
//        BindingMethod(type = android.widget.ImageView::class, attribute = "android:tintMode", method = "setImageTintMode"))
//class ImageViewBindingAdapter