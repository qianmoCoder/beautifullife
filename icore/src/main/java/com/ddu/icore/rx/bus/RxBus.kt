package com.ddu.icore.rx.bus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*

/**
 * Created by yzbzz on 2018/2/9.
 */

class RxBus private constructor() {

    companion object {

        fun get() = SingletonHolder.instance
        val instance: RxBus
            get() = SingletonHolder.instance

        @JvmOverloads
        fun action(action: String, actionCallBack: ActionCallBack<Any>? = null): Observable<*> {
            return instance.setAction(action, actionCallBack)
        }

        fun post(action: String, data: Any) {
            instance.sendPost(action, data)
        }
    }

    private object SingletonHolder {
        val instance = RxBus()
    }


    private val mSubjects = HashMap<String, PublishSubject<Any>>()


    private fun setAction(action: String, callBack: ActionCallBack<Any>?): Observable<*> {
        val publishSubject = PublishSubject.create<Any>()
        mSubjects[action] = publishSubject
        return publishSubject.doOnSubscribe {
            callBack?.execute(action)
        }
    }

    private fun sendPost(action: String, data: Any) {
        val publishSubject = mSubjects[action]
        if (null != publishSubject) {
            publishSubject.onNext(data)
            publishSubject.onComplete()
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
