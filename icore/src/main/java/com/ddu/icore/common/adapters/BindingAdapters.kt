package com.ddu.icore.common.adapters

import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.ddu.icore.common.ext.parseColor
import com.ddu.icore.common.ext.px2dp

/**
 * Created by yzbzz on 2019-08-08.
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

@BindingAdapter("renderHtml")
fun bindRenderHtml(view: TextView, description: String?) {
    if (description != null) {
        view.text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.movementMethod = LinkMovementMethod.getInstance()
    } else {
        view.text = ""
    }
}

@BindingAdapter("margin")
fun bindMargin(v: View, margin: String) {
    if (!TextUtils.isEmpty(margin)) {
        val margins = margin.split(" ")
        when (margin.length) {
            1 -> {
                val left = margins[0].px2dp(v.context).toInt()
                v.setPadding(left, v.paddingTop, v.paddingRight, v.paddingBottom)
            }
            2 -> {
                val left = margins[0].px2dp(v.context).toInt()
                val top = margins[1].px2dp(v.context).toInt()
                v.setPadding(left, top, v.paddingRight, v.paddingBottom)
            }
            3 -> {
                val left = margins[0].px2dp(v.context).toInt()
                val top = margins[1].px2dp(v.context).toInt()
                val right = margins[2].px2dp(v.context).toInt()
                v.setPadding(left, top, right, v.paddingBottom)
            }
            4 -> {
                val left = margins[0].px2dp(v.context).toInt()
                val top = margins[1].px2dp(v.context).toInt()
                val right = margins[2].px2dp(v.context).toInt()
                val bottom = margins[3].px2dp(v.context).toInt()
                v.setPadding(left, top, right, bottom)
            }
        }
    }
}