package com.ddu.icore.rx.pay

import com.ddu.icore.rx.bus.ActionCallBack
import com.ddu.icore.rx.bus.RxBus
import com.ddu.icore.rx.bus.RxBus.Companion.action
import io.reactivex.Observable


/**
 * Created by yzbzz on 2018/1/5.
 */

class RxWxPay {

    fun sendReq(prepayId: String): Observable<Any> {
        return action(prepayId, object : ActionCallBack<Any> {
            override fun execute(t: Any) {
            }
        })


    }

    fun post(prepayId: String) {
        RxBus.post(prepayId, "")
    }

}
