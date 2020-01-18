package com.ddu.ui.fragment.work.kotlin.notification.util

import android.text.format.DateUtils
import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 * Created by yzbzz on 2020/1/17.
 */
@BindingAdapter("elapsedTime")
fun TextView.setElapsedTime(value: Long) {
    val seconds = value / 1000
    text = if (seconds < 60) seconds.toString() else DateUtils.formatElapsedTime(seconds)
}