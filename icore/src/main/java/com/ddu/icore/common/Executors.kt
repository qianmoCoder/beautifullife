package com.ddu.icore.common

import android.os.Handler
import android.os.Looper
import java.util.concurrent.*
import java.util.concurrent.Executors
import kotlin.math.max
import kotlin.math.min

/**
 * Created by yzbzz on 2019-08-11.
 */
object Executors {

    val CPU_COUNT = Runtime.getRuntime().availableProcessors()
    val CORE_POOL_SIZE = max(2, min(CPU_COUNT - 1, 4))
    val MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1
    const val KEEP_ALIVE_SECONDS = 30L
    val sPoolWorkQueue = LinkedBlockingQueue<Runnable>(128)

    val DISK_IO_EXECUTOR = Executors.newSingleThreadExecutor()
    val NETWORK_IO_EXECUTOR = Executors.newFixedThreadPool(3)
    val MAIN_THREAD_EXECUTOR = MainThreadExecutor()

    val SCHEDULED_IO__EXECUTOR = Executors.newSingleThreadScheduledExecutor()

    var THREAD_POOL_EXECUTOR: ThreadPoolExecutor

    init {
        val threadPoolExecutor = ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
            sPoolWorkQueue
        )
        threadPoolExecutor.allowCoreThreadTimeOut(true)
        THREAD_POOL_EXECUTOR = threadPoolExecutor
    }

    fun ioThread(f: () -> Unit) = DISK_IO_EXECUTOR.execute(f)

    fun netWorkIoThread(f: () -> Unit) = NETWORK_IO_EXECUTOR.execute(f)

    fun mainThread(f: () -> Unit) = MAIN_THREAD_EXECUTOR.execute(f)

    fun excute(f: () -> Unit) = THREAD_POOL_EXECUTOR.execute(f)

    fun schedule(f: () -> Unit, delay: Long, unit: TimeUnit) = SCHEDULED_IO__EXECUTOR.schedule(f, delay, unit)

    fun scheduleAtFixedRat(f: () -> Unit, initialDelay: Long, period: Long, unit: TimeUnit) =
        SCHEDULED_IO__EXECUTOR.scheduleAtFixedRate(f, initialDelay, period, unit)

    class MainThreadExecutor : Executor {
        val handler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable?) {
            handler.post(command)
        }
    }
}

