package com.ddu.ui.fragment.work.kotlin

import android.content.Intent
import android.os.Bundle
import com.ddu.app.App
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

        val items = App.codeLabsProvider.getCodeLabsData("Kotlin_CodeLabs")
        items.sort()

        var pid = "0"
        items.forEach {
            if (pid != it.parentId) {
                pid = it.parentId
                val lessonParent = WorkEntity(it.parentId, TYPE_TITLE, title = "Lesson ${it.parentId}: ${it.parentContent}")
                mDataEntities.add(lessonParent)
            }
            val lesson = WorkEntity("${it.parentId}${it.id}", TYPE_CONTENT, number = "${it.parentId}.${it.id}",
                    content = it.content, clazz = it.cls, classType = it.classType)
            mDataEntities.add(lesson)
        }
    }

    override fun initView() {
        super.initView()
        mAdapter.setItemClickListener(this)
        setDefaultTitle("CodeLabs")
    }

    override fun getAdapter() = CodeLabsAdapter(mContext, mDataEntities)

    override fun accept(t: WorkEntity) {
        t.clazz?.apply {
            if ("1" == t.classType) {
                startActivity(Intent(mContext, this))
            } else {
                startFragment(this)
            }
        }
    }

}



