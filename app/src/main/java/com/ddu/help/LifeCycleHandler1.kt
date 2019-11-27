package com.ddu.help

import android.os.Handler
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * Created by yzbzz on 2019/11/23.
 */
open class LifeCycleHandler1(val owner: LifecycleOwner) : Handler(), LifecycleObserver {

    init {
        owner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        removeCallbacksAndMessages(null)
        Log.v("lhz", "removeCallbacksAndMessages")
        owner.lifecycle.removeObserver(this)
    }
}