package com.ddu.icore.common
import android.content.Context
import android.graphics.Color
import android.support.annotation.ColorInt
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.ddu.icore.ui.help.ShapeInjectHelper

fun View.hideKeyboard(): Boolean? {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    return inputMethodManager?.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

fun View.shapeRender(@ColorInt color: Int = Color.WHITE, radius: Float = 0f): ShapeInjectHelper {
    val shapeInjectHelper = ShapeInjectHelper(this)
    shapeInjectHelper.backgroundColor(color)
            .radius(radius)
    shapeInjectHelper.setBackground()
    return shapeInjectHelper
}
