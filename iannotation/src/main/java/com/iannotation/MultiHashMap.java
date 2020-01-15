package com.iannotation;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
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

    public boolean containsValue(ArrayList<V> value) {
        return model.containsValue(value);
    }

    public Set<Map.Entry<K, ArrayList<V>>> entrySet() {
        return model.entrySet();
    }

    public ArrayList<V> get(K key) {
        return model.get(key);
    }

    public boolean isEmpty() {
        return model.isEmpty();
    }

    public Set<K> keySet() {
        return model.keySet();
    }

    public Collection<ArrayList<V>> values() {
        return model.values();
    }

    public void put(K key, V value) {
        ArrayList<V> ls = model.get(key);
        if (ls == null) {
            ls = new ArrayList<>();
            model.put(key, ls);
        }
        if (!ls.contains(value)) {
            ls.add(value);
        }
    }

    public ArrayList<V> removeKey(K key) {
        return model.remove(key);
    }

    public void removeValueFromKey(K key, V val) {
        ArrayList<V> ls = model.get(key);
        if (ls != null) {
            ls.remove(val);
        }
    }

    public void removeValue(V val) {
        Collection<ArrayList<V>> values = values();
        for (ArrayList<V> value : values) {
            if (value == null || value.isEmpty()) {
                continue;
            }
            Iterator<V> iterator = value.iterator();
            while (iterator.hasNext()) {
                V v = iterator.next();
                if (Objects.equals(v, val)) {
                    iterator.remove();
                }
            }
        }
    }

    public int size() {
        return model.size();
    }

    public int fullSize() {
        int size = 0;
        Collection<ArrayList<V>> values = values();
        for (ArrayList<V> value : values) {
            if (value == null || value.isEmpty()) {
                continue;
            }
            size += value.size();
        }
        return size;
    }
}
