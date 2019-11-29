package com.ddu.icore.flow.workflow

import android.util.SparseArray
import androidx.core.util.forEach
import com.ddu.icore.callback.Consumer
import com.ddu.icore.common.ext.hasKey

/**
 * Created by yzbzz on 2019/11/27.
 */
class WorkFlow(var flowNodes: SparseArray<WorkNode>?) {

    private var recentNode: WorkNode? = null

    private var isDisposed = false

    // 标记为处理完成,调用后不再能执行任何开启的操作
    fun dispose() {
        reset()
        if (null != flowNodes) {
            flowNodes?.clear()
            flowNodes = null
            recentNode = null
        }
        this.isDisposed = true
    }

    fun addNode(workNode: WorkNode) {
        if (isDisposed) {
            throw IllegalStateException("you can not operate a disposed workflow")
        }
        flowNodes?.append(workNode.getId(), workNode)
    }

    fun start() {
        if (isDisposed) {
            throw IllegalStateException("you can not operate a disposed workflow")
        }
        flowNodes?.let {
            startWithNode(it.keyAt(0))
        }
    }

    fun startWithNode(startNodeId: Int) {
        if (isDisposed) {
            throw IllegalStateException("you can not operate a disposed workflow")
        }
        if (null == flowNodes) {
            return
        }
        if (!flowNodes.hasKey(startNodeId)) {
            return
        }
        reset()
        flowNodes?.apply {
            val startIndex = indexOfKey(startNodeId)
            val startNode = valueAt(startIndex)
            recentNode = startNode
            startNode.doWork(object : Consumer {
                override fun accept() {
                    findAndExecuteNextNodeIfExist(startIndex)
                }
            })
        }
    }

    fun continueWork() {
        if (isDisposed) {
            throw IllegalStateException("you can not operate a disposed workflow");
        }
        recentNode?.onCompleted()
    }

    fun revert() {
        check(!isDisposed) { "you can not operate a disposed workflow" }
        if (null != recentNode && null != flowNodes) {
            val recentIndex = flowNodes!!.indexOfValue(recentNode)
            val targetId = flowNodes!!.keyAt(recentIndex - 1)
            if (targetId >= 0) {
                startWithNode(targetId)
            }
        }
    }

    fun isDisposed(): Boolean {
        return isDisposed
    }

    fun getRecentNodeId(): Int {
        return recentNode?.getId() ?: -1
    }


    private fun findAndExecuteNextNodeIfExist(startIndex: Int) {
        val nextIndex = startIndex + 1
        if (!flowNodes.hasKey(nextIndex)) {
            return
        }
        val nextNode = flowNodes?.valueAt(nextIndex)
        nextNode?.apply {
            recentNode = nextNode
            nextNode.doWork(object : Consumer {
                override fun accept() {
                    findAndExecuteNextNodeIfExist(nextIndex)
                }
            })
        }
    }

    private fun reset() {
        flowNodes?.apply {
            forEach { key, _ ->
                get(key).removeCallback()
            }
        }
    }

    class Builder {
        val f = SparseArray<WorkNode>()

        fun withNode(node: WorkNode): Builder {
            this.f.append(node.getId(), node)
            return this
        }

        fun create() = WorkFlow(f)

    }

}