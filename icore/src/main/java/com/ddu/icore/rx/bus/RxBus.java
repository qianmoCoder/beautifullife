package com.ddu.icore.rx.bus;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by yzbzz on 2018/2/9.
 */

public class RxBus<T> {

    private Map<Integer, PublishSubject<T>> mSubjects = new HashMap<>();

    @NonNull
    public static RxBus getInstance() {
        return RxBus.SingletonHolder.instance;
    }

    private static class SingletonHolder {
        @NonNull
        private static RxBus instance = new RxBus();
    }

//    public static <T> Observable<T> action(int action) {
//        return Observable.just(3);
//    }

    private Observable<T> putAction(int action) {
        PublishSubject<T> publishSubject = PublishSubject.create();
        mSubjects.put(action, publishSubject);
        return publishSubject.doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
            }
        });
    }


    public void post(int action, T t) {
        PublishSubject publishSubject = mSubjects.get(action);
        if (null != publishSubject) {
            publishSubject.onNext(t);
            publishSubject.onComplete();
        }
    }
}
