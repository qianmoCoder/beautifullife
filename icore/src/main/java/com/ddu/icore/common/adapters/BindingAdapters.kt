package com.ddu.icore.common.adapters

import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.View.*
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.ddu.icore.common.ext.parseColor
import com.ddu.icore.common.ext.px2dp
import com.ddu.icore.sdk.SDKManager

/**
 * Created by yzbzz on 2019-08-08.
 */
@BindingAdapter("isGone")
fun View.bindIsGone(isGone: Boolean) {
    visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@set:BindingAdapter("visibleOrGone")
var View.visibleOrGone
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else GONE
    }

@set:BindingAdapter("visible")
var View.visible
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else INVISIBLE
    }

@set:BindingAdapter("invisible")
var View.invisible
    get() = visibility == INVISIBLE
    set(value) {
        visibility = if (value) INVISIBLE else VISIBLE
    }

@set:BindingAdapter("gone")
var View.gone
    get() = visibility == GONE
    set(value) {
        visibility = if (value) GONE else VISIBLE
    }

@BindingAdapter(value = ["bg_radius", "bg_color", "bg_color_s"], requireAll = false)
fun View.bindBackground(radius: Float, @ColorInt color: Int?, colorString: String?) {
    val gd = GradientDrawable()

    if (null != color) {
        gd.setColor(color)
    } else if (null != colorString) {
        gd.setColor(colorString.parseColor())
    }
    gd.cornerRadius = radius
    background = gd
}

@BindingAdapter("renderHtml")
fun TextView.bindRenderHtml(description: String?) {
    if (description != null) {
        text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
        movementMethod = LinkMovementMethod.getInstance()
    } else {
        text = ""
    }
}

@BindingAdapter("margin")
fun View.bindMargin(margin: String) {
    if (!TextUtils.isEmpty(margin)) {
        val margins = margin.split(" ")
        when (margin.length) {
            1 -> {
                val left = margins[0].px2dp(context).toInt()
                setPadding(left, paddingTop, paddingRight, paddingBottom)
            }
            2 -> {
                val left = margins[0].px2dp(context).toInt()
                val top = margins[1].px2dp(context).toInt()
                setPadding(left, top, paddingRight, paddingBottom)
            }
            3 -> {
                val left = margins[0].px2dp(context).toInt()
                val top = margins[1].px2dp(context).toInt()
                val right = margins[2].px2dp(context).toInt()
                setPadding(left, top, right, paddingBottom)
            }
            4 -> {
                val left = margins[0].px2dp(context).toInt()
                val top = margins[1].px2dp(context).toInt()
                val right = margins[2].px2dp(context).toInt()
                val bottom = margins[3].px2dp(context).toInt()
                setPadding(left, top, right, bottom)
            }
        }
    }
}

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String) {
    SDKManager.instance.getImageLoader()?.loadUrl(url, this)
}