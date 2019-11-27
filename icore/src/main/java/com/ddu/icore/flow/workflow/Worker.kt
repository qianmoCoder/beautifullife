package com.ddu.icore.flow.workflow

/**
 * Created by yzbzz on 2019/11/27.
 */
interface Worker {

    fun doWork(current: Node)
}