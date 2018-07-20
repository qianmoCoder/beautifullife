package com.ddu.aspect

import android.app.Activity
import android.view.KeyEvent
import android.view.Window

/**
 * Created by yzbzz on 2018/1/16.
 */

class MonitorCallback(val mAcitivty: Activity, val mCallback: Window.Callback) : Window.Callback by mCallback {

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        return mCallback.dispatchKeyEvent(event)
    }

}
