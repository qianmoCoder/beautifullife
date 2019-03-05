package com.ddu.icore.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by yzbzz on 2018/8/15.
 */
public class GenericsUtils {

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClassByGeneric(Class<?> subclass, int index) {
        Class<T> cls;
        try {
            Type superclass = subclass.getGenericSuperclass();
            ParameterizedType parameterized = (ParameterizedType) superclass;
            cls = (Class<T>) parameterized.getActualTypeArguments()[index];
        } catch (Exception e) {
            cls = null;
        }
        return cls;
    }

}
