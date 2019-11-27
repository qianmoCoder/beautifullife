package com.ddu.icore.flow.workflow

import com.ddu.icore.callback.Consumer

/**
 * Created by yzbzz on 2019/11/27.
 */
class WorkNode(private val nodeId: Int, val worker: Worker) : Node {

    private var mConsumer: Consumer? = null

    override fun getId() = nodeId

    override fun onCompleted() {
        mConsumer?.accept()
    }

    fun doWork(consumer: Consumer) {
        this.mConsumer = consumer
        worker.doWork(this)
    }

    fun removeCallback() {
        this.mConsumer = null;
    }

    override fun toString(): String {
        return "nodeId: ${getId()}"
    }

    companion object {

        fun build(nodeId: Int, worker: Worker) = WorkNode(nodeId, worker)
    }
}