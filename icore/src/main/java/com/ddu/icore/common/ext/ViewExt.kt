package com.ddu.icore.common.ext

import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.Size

fun View.alphaAnimator(duration: Long = 300, vararg values: Float): ObjectAnimator {
    val objectAnimator = ObjectAnimator.ofFloat(this, View.ALPHA, *values)
    objectAnimator.duration = duration
    return objectAnimator
}

fun View.rotationX(duration: Long, vararg values: Float): ObjectAnimator {
    val objectAnimator = ObjectAnimator.ofFloat(this, View.ROTATION_X, *values)
    objectAnimator.duration = duration
    return objectAnimator
}

fun View.rotationY(duration: Long, vararg values: Float): ObjectAnimator {
    val objectAnimator = ObjectAnimator.ofFloat(this, View.ROTATION_Y, *values)
    objectAnimator.duration = duration
    return objectAnimator
}

fun View.hideKeyboard(): Boolean? {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    return inputMethodManager?.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

fun TextView.setTextColor(@Size(min = 1) colorString: String) {
    setTextColor(colorString.parseColor())
}


