package com.ddu.icore.common

import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import kotlin.math.max
import kotlin.math.min

/**
 * Created by yzbzz on 2019-08-11.
 */
object Executors {

    private val CPU_COUNT = Runtime.getRuntime().availableProcessors()
    private val CORE_POOL_SIZE = max(2, min(CPU_COUNT - 1, 4))
    private val MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1
    private const val KEEP_ALIVE_SECONDS = 30L
    private val sPoolWorkQueue = LinkedBlockingQueue<Runnable>(128)

    private val IO_EXECUTOR = Executors.newSingleThreadExecutor()
    private var THREAD_POOL_EXECUTOR: ThreadPoolExecutor

    private val IO_SCHEDULED_EXECUTOR = Executors.newSingleThreadScheduledExecutor()

    init {
        val threadPoolExecutor = ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
            sPoolWorkQueue
        )
        threadPoolExecutor.allowCoreThreadTimeOut(true)
        THREAD_POOL_EXECUTOR = threadPoolExecutor
    }

    fun ioThread(f: () -> Unit) = IO_EXECUTOR.execute(f)

    fun excute(f: () -> Unit) = THREAD_POOL_EXECUTOR.execute(f)

    fun schedule(f: () -> Unit, delay: Long, unit: TimeUnit) = IO_SCHEDULED_EXECUTOR.schedule(f, delay, unit)

    fun scheduleAtFixedRat(f: () -> Unit, initialDelay: Long, period: Long, unit: TimeUnit) =
        IO_SCHEDULED_EXECUTOR.scheduleAtFixedRate(f, initialDelay, period, unit)

}

