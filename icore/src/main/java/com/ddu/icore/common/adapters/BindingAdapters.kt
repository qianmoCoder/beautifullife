package com.ddu.icore.common.adapters

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.View.*
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ddu.icore.common.ext.parseColor
import com.ddu.icore.common.ext.px2dp

/**
 * Created by yzbzz on 2019-08-08.
 */
@BindingAdapter("isGone")
fun View.bindIsGone(isGone: Boolean) {
    visibility = if (isGone) {
        GONE
    } else {
        VISIBLE
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

@BindingAdapter(value = ["color", "color_focused", "color_disable"], requireAll = false)
fun TextView.bindTextColor(@ColorInt color: Int, @ColorInt colorFocused: Int?, @ColorInt colorDisable: Int? = color) {

    val mStates = arrayOfNulls<IntArray>(4)
    mStates[0] = intArrayOf(android.R.attr.state_focused)
    mStates[1] = intArrayOf(android.R.attr.state_pressed)
    mStates[2] = intArrayOf(-android.R.attr.state_enabled)
    mStates[3] = intArrayOf()

    val mColors = IntArray(4)
    mColors[0] = colorFocused ?: color
    mColors[1] = colorFocused ?: color
    mColors[2] = colorDisable ?: color
    mColors[3] = color

    setTextColor(ColorStateList(mStates, mColors))
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

@BindingAdapter(value = ["imageUrl", "placeholder"], requireAll = false)
fun ImageView.setImageUrl(url: String, @DrawableRes resourceId: Int? = null) {
    if (resourceId != null) {
        Glide.with(context).load(url).placeholder(resourceId).into(this)
    } else {
        Glide.with(context).load(url).into(this)
    }

}