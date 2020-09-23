package com.ddu.icore.common.ext

import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.Size
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

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

/**
 * 等待 View 被布局完成
 * 比如说，我们改变了一个 TextView 中的内容，需要等待布局事件完成后才能获取该控件的新尺寸
 */
suspend fun View.awaitNextLayout() = suspendCancellableCoroutine<Unit> { cont ->

    val listener = object: View.OnLayoutChangeListener {

        override fun onLayoutChange(view: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
            view.removeOnLayoutChangeListener(this)
            cont.resume(Unit)
        }
    }

    cont.invokeOnCancellation { removeOnLayoutChangeListener(listener) }
    addOnLayoutChangeListener(listener)
}


