package com.ddu.icore.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class CollectionUtil {

    @NonNull
    public static <K, V> HashMap<K, V> hashMap() {
        return new HashMap<K, V>();
    }

    @NonNull
    public static <K, V> LinkedHashMap<K, V> linkedHashMap() {
        return new LinkedHashMap<K, V>();
    }

    @NonNull
    public static <T> List<T> arrayList() {
        return new ArrayList<T>();
    }

    @NonNull
    public static <T> List<T> linkedList() {
        return new LinkedList<T>();
    }

    @NonNull
    public static <T> Set<T> hashSet() {
        return new HashSet<T>();
    }

    @NonNull
    public static <T> Queue<T> queue() {
        return new LinkedList<T>();
    }

    public static boolean isEmpty(@Nullable Collection collection) {
        return null == collection || collection.isEmpty();
    }

}
