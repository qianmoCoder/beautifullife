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

public class RxBus {

    private Map<String, PublishSubject> mSubjects = new HashMap<>();

    @NonNull
    public static RxBus getInstance() {
        return RxBus.SingletonHolder.instance;
    }

    private static class SingletonHolder {
        @NonNull
        private static RxBus instance = new RxBus();
    }

    public static Observable action(String action) {
        return action(action, null);
    }

    public static Observable action(String action, ActionCallBack actionCallBack) {
        return getInstance().setAction(action, actionCallBack);
    }

    private Observable setAction(final String action, final ActionCallBack callBack) {
        PublishSubject publishSubject = PublishSubject.create();
        mSubjects.put(action, publishSubject);
        return publishSubject.doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                if (null != callBack) {
                    callBack.execute(action);
                }
            }
        });
    }

    public static <T> void post(String action, T data) {
        getInstance().sendPost(action, data);
    }

    private <T> void sendPost(String action, T data) {
        PublishSubject<T> publishSubject = mSubjects.get(action);
        if (null != publishSubject) {
            publishSubject.onNext(data);
            publishSubject.onComplete();
        }
    }

//    public enum RxBusSingleton implements RxBusInterface<Object> {
//
//        INSTANCE;
//
//        public static <T> RxBusInterface<T> getListener() {
//            return (RxBusInterface<T>) INSTANCE;
//        }
//
//        public void handleEvent(Object event) {
//
//        }
//    }
//
//    private interface RxBusInterface<T> {
//        void handleEvent(T event);
//    }
}
