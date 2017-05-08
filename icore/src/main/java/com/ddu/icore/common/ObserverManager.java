package com.ddu.icore.common;


import android.support.annotation.NonNull;

import com.ddu.icore.aidl.GodIntent;
import com.ddu.icore.util.MultiHashMap;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class ObserverManager {

    @NonNull
    private MultiHashMap<Integer, WeakReference<IObserver>> observers = new MultiHashMap<>();

    @NonNull
    public static ObserverManager getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        @NonNull
        private static ObserverManager instance = new ObserverManager();
    }

    public void registerObserver(int action, IObserver observer) {
        synchronized (observers) {
            observers.put(action, new WeakReference<>(observer));
        }
    }

    public void unRegisterObserver(IObserver observer) {
        synchronized (observers) {
            Set<Map.Entry<Integer, ArrayList<WeakReference<IObserver>>>> entrySet = observers.entrySet();
            for (Map.Entry<Integer, ArrayList<WeakReference<IObserver>>> entry : entrySet) {
                ArrayList<WeakReference<IObserver>> list = entry.getValue();
                if (list == null || list.size() == 0) {
                    continue;
                }
                Iterator<WeakReference<IObserver>> iterator = list.iterator();
                while (iterator.hasNext()) {
                    WeakReference<IObserver> uiObserver = iterator.next();
                    if (observer == uiObserver.get()) {
                        iterator.remove();
                    }
                }
            }
        }
    }

    public void unRegisterObserver(int action, IObserver observer) {
        synchronized (observers) {
            ArrayList<WeakReference<IObserver>> list = observers.get(action);
            if (list == null || list.size() == 0) {
                return;
            }
            Iterator<WeakReference<IObserver>> iterator = list.iterator();
            while (iterator.hasNext()) {
                WeakReference<IObserver> uiObserver = iterator.next();
                if (observer == uiObserver.get()) {
                    iterator.remove();
                }
            }
        }
    }

    public void notify(@NonNull GodIntent godIntent) {
        int action = godIntent.getAction();
        notify(action);
    }

    public void notify(int action) {
        ArrayList<WeakReference<IObserver>> listeners = observers.get(action);
        if (null == listeners || listeners.size() == 0) {
            return;
        }
        listeners.removeAll(Collections.singleton(null));
        for (WeakReference<IObserver> weakListener : listeners) {
            final IObserver listener = weakListener.get();
            if (listener != null) {
                listener.onReceiverNotify(action);
            }
        }
    }
}
