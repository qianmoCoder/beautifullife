package com.ddu.icore.rx.pay;

import com.ddu.icore.rx.bus.ActionCallBack;
import com.ddu.icore.rx.bus.RxBus;

import io.reactivex.Observable;


/**
 * Created by yzbzz on 2018/1/5.
 */

public class RxWxPay {

    public Observable<RxPayResultInfo> sendReq(String prepayId) {
        return RxBus.action(prepayId, new ActionCallBack() {
            @Override
            public void execute(Object o) {
                // doSomething
            }
        });
    }

    public void post(String prepayId) {
        RxBus.post(prepayId, "");
    }

}
