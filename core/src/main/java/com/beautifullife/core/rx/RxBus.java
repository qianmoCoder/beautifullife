package com.beautifullife.core.rx;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by admin on 2015/10/14.
 * <p>
 * send(new TapEvent())
 * <p>
 * toObserverable().subscribe(new Action1<Object>() {
 *
 * @Override public void call(Object event) {
 * if(event instanceof  TapEvent){
 * //TODO:
 * }else if(event instanceof  otherEvent){
 * //TODO:
 * }
 * }
 * });
 */
public class RxBus {
    //private final PublishSubject<Object> _bus = PublishSubject.create();

    // If multiple threads are going to emit events to this
    // then it must be made thread-safe like this instead
    private final Subject<Object, Object> _bus = new SerializedSubject<>(PublishSubject.create());

    public void send(Object o) {
        _bus.onNext(o);
    }

    public Observable<Object> toObserverable() {
        return _bus;
    }

    public boolean hasObservers() {
        return _bus.hasObservers();
    }
}
