package com.dream.beautifullife.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Created by admin on 2015/9/6.
 */
public class CollectionUtil {

    public static <K,V> Map<K,V> hashMap(){
        return new HashMap<K,V>();
    }

    public static <K, V> Map<K, V> linkedHashMap() {
        return new LinkedHashMap<K, V>();
    }

    public static <T> List<T> arraylist() {
        return new ArrayList<T>();
    }

    public static <T> List<T> linkedlist() {
        return new LinkedList<T>();
    }

    public static <T> Set<T> hashSet() {
        return new HashSet<T>();
    }

    public static <T> Queue<T> queue() {
        return new LinkedList<T>();
    }
}
