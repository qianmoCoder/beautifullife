package com.ddu.icore.common

import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.alphaAnimator(duration: Long, vararg values: Float): ObjectAnimator {
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


