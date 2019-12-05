package com.ddu.ui.fragment.study.customer

import com.ddu.R
import com.ddu.icore.flow.workflow.Node
import com.ddu.icore.flow.workflow.WorkFlow
import com.ddu.icore.flow.workflow.WorkNode
import com.ddu.icore.flow.workflow.Worker
import com.ddu.icore.ui.fragment.DefaultFragment
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_study_work_flow.*

/**
 * Created by yzbzz on 2019/11/27.
 */
@IElement("customer")
class WorkFlowFragment : DefaultFragment() {

    companion object {
        const val FIRST = 1
        const val SECOND = 2
        const val THREE = 3
    }

    override fun getLayoutId() = R.layout.fragment_study_work_flow

    override fun initView() {
        tv_begin.setOnClickListener {
            val workFlow = WorkFlow.Builder()
                    .withNode(getFirstNode(FIRST))
                    .withNode(getFirstNode(SECOND))
                    .withNode(getFirstNode(THREE))
                    .create()
            workFlow.start()
        }
    }

    private fun getFirstNode(id: Int): WorkNode {
        return WorkNode.build(id, object : Worker {
            override fun doWork(current: Node) {
                Thread.sleep(2000)
                current.onCompleted()
            }
        })
    }
}