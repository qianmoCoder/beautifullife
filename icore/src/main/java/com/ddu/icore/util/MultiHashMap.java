package com.ddu.icore.util;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MultiHashMap<K, V> {
    private HashMap<K, ArrayList<V>> model;

    public MultiHashMap() {
        this(16);
    }

    public MultiHashMap(int capacity) {
        model = new LinkedHashMap<>(capacity < 8 ? 8 : capacity);
    }

    public void clear() {
        model.clear();
    }

    public boolean containsKey(K key) {
        return model.containsKey(key);
    }

    public boolean containsValue(V value) {
        return model.containsValue(value);
    }

    @NonNull
    public Set<Map.Entry<K, ArrayList<V>>> entrySet() {
        return model.entrySet();
    }

    public ArrayList<V> get(K key) {
        return model.get(key);
    }

    public boolean isEmpty() {
        return model.isEmpty();
    }

    @NonNull
    public Set<K> keySet() {
        return model.keySet();
    }

    public void put(K key, V value) {
        ArrayList<V> ls = model.get(key);
        if (ls == null) {
            ls = new ArrayList<V>();
            model.put(key, ls);
        }
        if (!ls.contains(value)) {
            ls.add(value);
        }
    }

    public ArrayList<V> remove(K key) {
        return model.remove(key);
    }

    public void remove(K key, V val) {
        ArrayList<V> ls = model.get(key);
        if (ls != null) {
            ls.remove(val);
        }
    }

    public void removeValue(V val) {
        for (Map.Entry<K, ArrayList<V>> e : model.entrySet()) {
            ArrayList<V> ls = e.getValue();
            for (int i = 0; i < ls.size(); ) {
                V v = ls.get(i);
                if (v == val || (v != null && v.equals(val))) {
                    e.getValue().remove(val);
                    continue;
                }
                ++i;
            }
        }
    }

    public int size() {
        return model.size();
    }

    @NonNull
    public Collection<ArrayList<V>> values() {
        return model.values();
    }
}
