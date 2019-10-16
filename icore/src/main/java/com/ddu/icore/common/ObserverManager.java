package com.ddu.icore.common;


import androidx.annotation.NonNull;

import com.ddu.icore.aidl.GodIntent;
import com.ddu.icore.util.MultiHashMap;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;


public class ObserverManager {

    @NonNull
    private MultiHashMap<String, WeakReference<IObserver>> observers = new MultiHashMap<>();

    @NonNull
    public static ObserverManager getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        @NonNull
        private static ObserverManager instance = new ObserverManager();
    }

    public void registerObserver(String action, IObserver observer) {
        synchronized (this) {
            observers.put(action, new WeakReference<>(observer));
        }
    }

    public void unRegisterObserver(IObserver observer) {
        synchronized (this) {
            Collection<ArrayList<WeakReference<IObserver>>> values = observers.values();
            for (ArrayList<WeakReference<IObserver>> value : values) {
                if (value == null || value.isEmpty()) {
                    continue;
                }
                Iterator<WeakReference<IObserver>> iterator = value.iterator();
                while (iterator.hasNext()) {
                    WeakReference<IObserver> uiObserver = iterator.next();
                    if (uiObserver != null) {
                        IObserver iObserver = uiObserver.get();
                        if (Objects.equals(iObserver, observer)) {
                            iterator.remove();
                        }
                    }

                }
            }
        }
    }

    public void unRegisterObserver(String action, IObserver observer) {
        synchronized (this) {
            ArrayList<WeakReference<IObserver>> value = observers.get(action);
            if (value == null || value.isEmpty()) {
                return;
            }
            Iterator<WeakReference<IObserver>> iterator = value.iterator();
            while (iterator.hasNext()) {
                WeakReference<IObserver> uiObserver = iterator.next();
                if (uiObserver != null) {
                    IObserver iObserver = uiObserver.get();
                    if (Objects.equals(iObserver, observer)) {
                        iterator.remove();
                    }
                }
            }
        }
    }

    public void notify(@NonNull GodIntent godIntent) {
        String action = godIntent.getAction();
        ArrayList<WeakReference<IObserver>> listeners = observers.get(action);
        if (null == listeners || listeners.isEmpty()) {
            return;
        }
        listeners.removeAll(Collections.singleton(null));
        for (WeakReference<IObserver> weakListener : listeners) {
            final IObserver listener = weakListener.get();
            if (listener != null) {
                listener.onReceiverNotify(godIntent);
            }
        }
    }

    public void notify(String action) {
        GodIntent godIntent = new GodIntent();
        godIntent.setAction(action);
        notify(godIntent);
    }
}
