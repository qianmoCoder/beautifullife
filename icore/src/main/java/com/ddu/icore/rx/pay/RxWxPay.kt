package com.ddu.icore.rx.pay

import com.ddu.icore.rx.bus.RxBus
import com.ddu.icore.rx.bus.RxBus.action
import io.reactivex.Observable


/**
 * Created by yzbzz on 2018/1/5.
 */

class RxWxPay {

    fun sendReq(prepayId: String): Observable<Any> {
        return action(prepayId) {

        }
    }

    fun post(prepayId: String) {
        RxBus.post(prepayId, "")
    }

}
