package com.ddu.icore.common.ext

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import com.ddu.icore.util.sys.PreferenceUtils
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.locks.Lock
import kotlin.coroutines.resume

/**
 * Created by yzbzz on 2018/3/12.
 */
@Suppress("REIFIED_TYPE_PARAMETER_NO_INLINE")
fun <reified T : Any> Set<*>.isSetOf(): Boolean =
        T::class.java.isAssignableFrom(this::class.java.componentType!!)


inline fun <reified T : Any> Class<*>.isClassOf(): Boolean =
        T::class.java.isAssignableFrom(this::class.java.componentType!!)

fun <T1, T2> ifNotNull(value1: T1?, value2: T2?, bothNotNull: (T1, T2) -> (Unit)) {
    if (value1 != null && value2 != null) {
        bothNotNull(value1, value2)
    }
}

inline fun <reified T> findPreference(key: String, default: T): T? =
        PreferenceUtils.findPreference(key, default)

inline fun <reified T> applyPreference(key: String, value: T) = PreferenceUtils.apply(key, value)

inline fun <reified T> commitPreference(key: String, value: T) = PreferenceUtils.commit(key, value)

inline fun <T> method(lock: Lock, body: () -> T): T {
    lock.lock()
    try {
        return body()
    } finally {
        lock.unlock()
    }
}

/**
 * 等待 Animator 执行完成
 */
suspend fun Animator.awaitEnd() = suspendCancellableCoroutine<Unit> { cont ->
    cont.invokeOnCancellation { cancel() }

    addListener(object : AnimatorListenerAdapter() {
        private var endedSuccessfully = true

        override fun onAnimationCancel(animation: Animator) {
            // 动画已经被取消，修改是否成功结束的标志
            endedSuccessfully = false
        }

        override fun onAnimationEnd(animation: Animator) {
            // 为了在协程恢复后的不发生泄漏，需要确保移除监听
            animation.removeListener(this)
            if (cont.isActive) {

                // 如果协程仍处于活跃状态
                if (endedSuccessfully) {
                    // 并且动画正常结束，恢复协程
                    cont.resume(Unit)
                } else {
                    // 否则动画被取消，同时取消协程
                    cont.cancel()
                }
            }
        }
    })
}
