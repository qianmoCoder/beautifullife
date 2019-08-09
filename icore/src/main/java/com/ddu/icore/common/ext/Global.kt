package com.ddu.icore.common.ext

/**
 * Created by yzbzz on 2018/3/12.
 */
@Suppress("REIFIED_TYPE_PARAMETER_NO_INLINE")
fun <reified T : Any> Set<*>.isSetOf(): Boolean =
        T::class.java.isAssignableFrom(this::class.java.componentType)

fun <T1, T2> ifNotNull(value1: T1?, value2: T2?, bothNotNull: (T1, T2) -> (Unit)) {
    if (value1 != null && value2 != null) {
        bothNotNull(value1, value2)
    }
}
