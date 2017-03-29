package com.ddu.icore.common;


import android.support.annotation.NonNull;

import com.ddu.icore.aidl.GodIntent;
import com.ddu.icore.app.BaseApp;
import com.ddu.icore.util.MultiHashMap;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class ObserverManager {

    @NonNull
    private MultiHashMap<Integer, WeakReference<BaseObserver>> observers = new MultiHashMap<>();
    @NonNull
    private MultiHashMap<Integer, WeakReference<BaseObserver>> listeners = new MultiHashMap<>();

    @NonNull
    public static ObserverManager getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        @NonNull
        private static ObserverManager instance = new ObserverManager();
    }

    public void registerObserver(int action, BaseObserver observer) {
        synchronized (observers) {
            observers.put(action, new WeakReference<>(observer));
        }
    }

    public void unRegisterObserver(BaseObserver observer) {
        synchronized (observers) {
            Set<Map.Entry<Integer, ArrayList<WeakReference<BaseObserver>>>> entrySet = observers.entrySet();
            for (Map.Entry<Integer, ArrayList<WeakReference<BaseObserver>>> entry : entrySet) {
                ArrayList<WeakReference<BaseObserver>> list = entry.getValue();
                if (list == null || list.size() == 0) {
                    continue;
                }
                Iterator<WeakReference<BaseObserver>> iterator = list.iterator();
                while (iterator.hasNext()) {
                    WeakReference<BaseObserver> uiObserver = iterator.next();
                    if (observer == uiObserver.get()) {
                        iterator.remove();
                    }
                }
            }
        }
    }

    public void unRegisterObserver(int action, BaseObserver observer) {
        synchronized (observers) {
            ArrayList<WeakReference<BaseObserver>> list = observers.get(action);
            if (list == null || list.size() == 0) {
                return;
            }
            Iterator<WeakReference<BaseObserver>> iterator = list.iterator();
            while (iterator.hasNext()) {
                WeakReference<BaseObserver> uiObserver = iterator.next();
                if (observer == uiObserver.get()) {
                    iterator.remove();
                }
            }
        }
    }

    public void notify(@NonNull GodIntent godIntent) {
        int action = godIntent.getAction();
        final GodIntent intent = godIntent;
        ArrayList<WeakReference<BaseObserver>> listeners = observers.get(action);
        if (null == listeners || listeners.size() == 0) {
            return;
        }
        listeners.removeAll(Collections.singleton(null));
        for (WeakReference<BaseObserver> weakListener : listeners) {
            final BaseObserver listener = weakListener.get();
            if (listener != null) {
                BaseApp.post(new Runnable() {
                    public void run() {
                        try {
                            listener.onReceiverNotify(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    public void notifyAll(int action) {
        final GodIntent godIntent = new GodIntent();
        godIntent.setAction(action);
        ArrayList<WeakReference<BaseObserver>> listeners = observers.get(action);
        if (null == listeners || listeners.size() == 0) {
            return;
        }
        listeners.removeAll(Collections.singleton(null));
        for (WeakReference<BaseObserver> weakListener : listeners) {
            final BaseObserver listener = weakListener.get();
            if (listener != null) {
                BaseApp.post(new Runnable() {
                    public void run() {
                        try {
                            listener.onReceiverNotify(godIntent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }
}
