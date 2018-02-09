package com.ddu.icore.rx.pay;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;


/**
 * Created by yzbzz on 2018/1/5.
 */

public class RxWxPay {

    private static Map<String, PublishSubject<RxPayResultInfo>> mSubjects = new HashMap<>();
    private Context mContext;

    private RxWxPay(Context context) {
        mContext = context;
    }

    public Observable<RxPayResultInfo> sendReq(String prepayId) {
        PublishSubject<RxPayResultInfo> payRespPublishSubject = PublishSubject.create();
        mSubjects.put(prepayId, payRespPublishSubject);
        return payRespPublishSubject.doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                //
            }

        });
    }

    public static RxWxPay with(Context context) {
        return new RxWxPay(context);
    }

    public static PublishSubject getPublishSubject(String prepayid) {
        return mSubjects.remove(prepayid);
    }

}
