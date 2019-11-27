package com.ddu.help

import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import java.lang.ref.WeakReference

/**
 * Created by yzbzz on 2019/11/23.
 */
open class LifeCycleHandler(owner: LifecycleOwner, val block: (Message) -> Unit) : Handler(), LifecycleObserver {

    private val mOwner: WeakReference<LifecycleOwner> = WeakReference(owner)

    init {
        mOwner.get()?.apply {
            lifecycle.addObserver(LifecycleBoundObserver())
        }
    }

    inner class LifecycleBoundObserver : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            val state = source.lifecycle.currentState
            if (state == Lifecycle.State.DESTROYED) {
                removeCallbacksAndMessages(null)
                source.lifecycle.removeObserver(this)
                Log.v("lhz", "LifeCycleHandle removeCallbacksAndMessages")
            }
        }
    }

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        val owner = mOwner.get()
        if (owner != null) {
            block(msg)
        }
    }
}