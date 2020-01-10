package com.ddu.ui.fragment.work.kotlin

import android.os.Bundle
import com.ddu.icore.callback.InConsumer1
import com.ddu.icore.ui.fragment.AbsRVFragment
import com.ddu.model.TYPE_CONTENT
import com.ddu.model.TYPE_TITLE
import com.ddu.model.WorkEntity
import com.ddu.ui.adapter.CodeLabsAdapter

/**
 * Created by yzbzz on 2018/6/8.
 */
class CodeLabsFragment : AbsRVFragment<WorkEntity, CodeLabsAdapter>(), InConsumer1<WorkEntity> {

    override fun initData(savedInstanceState: Bundle?) {
        val lesson1 = WorkEntity("1", TYPE_TITLE, title = "Lesson 1: Notifications")
        mDataEntities.add(lesson1)

        val lesson2 = WorkEntity("2", TYPE_TITLE, title = "Lesson 2: Advanced Graphics")
        mDataEntities.add(lesson2)
        val lesson21 = WorkEntity("21", TYPE_CONTENT, number = "2.1", content = "Creating Custom Views", fragmentName = DialViewFragment::class.java.name)
        val lesson22 = WorkEntity("22", TYPE_CONTENT, number = "2.2", content = "Drawing on Canvas Objects")
        val lesson23 = WorkEntity("23", TYPE_CONTENT, number = "2.3", content = "Clipping Canvas Objects")
        val lesson24 = WorkEntity("24", TYPE_CONTENT, number = "2.4", content = "Creating Effects with Shaders")
        mDataEntities.add(lesson21)
        mDataEntities.add(lesson22)
        mDataEntities.add(lesson23)
        mDataEntities.add(lesson24)

        val lesson3 = WorkEntity("3", TYPE_TITLE, title = "Lesson 3: Animation")
        mDataEntities.add(lesson3)
    }

    override fun initView() {
        super.initView()
        mAdapter.setItemClickListener(this)
        setDefaultTitle("CodeLabs")
    }

    override fun getAdapter() = CodeLabsAdapter(mContext, mDataEntities)

    override fun accept(t: WorkEntity) {
       t.fragmentName?.let {
           startFragment(it)
       }
    }

}



