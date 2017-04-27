package com.ddu.icore.util;

import java.lang.reflect.ParameterizedType;

/**
 * Created by yzbzz on 2017/4/19.
 */

public class GenericUtils {

    public <T> Class<T> getType(int index) {
        Class<T> cls = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[index];
        return cls;
    }

}
