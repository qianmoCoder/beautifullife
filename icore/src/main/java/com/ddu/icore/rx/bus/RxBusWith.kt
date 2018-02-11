package com.ddu.icore.rx.bus

/**
 * Created by yzbzz on 2018/2/9.
 */

class RxBusWith(private val action: Any) {

    private var mActionCallBack: ActionCallBack<Any>? = null

    var actionCallBack: ActionCallBack<Any>?
        get() = mActionCallBack
        set(actionCallBack) {
            this.mActionCallBack = actionCallBack
        }
}
