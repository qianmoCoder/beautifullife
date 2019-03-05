package com.ddu.sdk

import android.widget.ImageView

/**
 * Created by yzbzz on 2018/8/27.
 */
object SDKHelper {

    fun <T> loadUrl(t: T, imageView: ImageView) {
        SDKManager.get().getImageLoader().loadUrl(t, imageView)
    }
}
