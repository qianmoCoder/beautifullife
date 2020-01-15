package com.ddu.ui.fragment.work.kotlin

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
            val lesson = WorkEntity("${it.parentId}${it.id}", TYPE_CONTENT, number = "${it.parentId}.${it.id}", content = it.content, clazz = it.cls)
            mDataEntities.add(lesson)
        }

//        val lesson1 = WorkEntity("1", TYPE_TITLE, title = "Lesson 1: Notifications")
//        mDataEntities.add(lesson1)
//        val lesson11 = WorkEntity("11", TYPE_CONTENT, number = "1.1", content = "Using Android Notifications", fragmentName = NotificationFragment::class.java.name)
//        val lesson12 = WorkEntity("12", TYPE_CONTENT, number = "1.2", content = "Android Firebase Cloud Messaging")
//        mDataEntities.add(lesson11)
//        mDataEntities.add(lesson12)
//
//        val lesson2 = WorkEntity("2", TYPE_TITLE, title = "Lesson 2: Advanced Graphics")
//        mDataEntities.add(lesson2)
//        val lesson21 = WorkEntity("21", TYPE_CONTENT, number = "2.1", content = "Creating Custom Views", fragmentName = DialViewFragment::class.java.name)
//        val lesson22 = WorkEntity("22", TYPE_CONTENT, number = "2.2", content = "Drawing on Canvas Objects")
//        val lesson23 = WorkEntity("23", TYPE_CONTENT, number = "2.3", content = "Clipping Canvas Objects")
//        val lesson24 = WorkEntity("24", TYPE_CONTENT, number = "2.4", content = "Creating Effects with Shaders")
//        mDataEntities.add(lesson21)
//        mDataEntities.add(lesson22)
//        mDataEntities.add(lesson23)
//        mDataEntities.add(lesson24)
//
//        val lesson3 = WorkEntity("3", TYPE_TITLE, title = "Lesson 3: Animation")
//        mDataEntities.add(lesson3)
//        val lesson31 = WorkEntity("31", TYPE_CONTENT, number = "3.1", content = "Property Animation")
//        val lesson32 = WorkEntity("32", TYPE_CONTENT, number = "3.2", content = "Animation with MotionLayout")
//        mDataEntities.add(lesson31)
//        mDataEntities.add(lesson32)
//
//        val lesson4 = WorkEntity("4", TYPE_TITLE, title = "Lesson 4: Geo")
//        mDataEntities.add(lesson4)
//        val lesson41 = WorkEntity("41", TYPE_CONTENT, number = "4.1", content = "Android Google Maps")
//        val lesson42 = WorkEntity("42", TYPE_CONTENT, number = "4.2", content = "Adding Geofencing to Your Map")
//        mDataEntities.add(lesson41)
//        mDataEntities.add(lesson42)
//
//        val lesson5 = WorkEntity("5", TYPE_TITLE, title = "Lesson 5: Testing and Dependency Injection")
//        mDataEntities.add(lesson5)
//        val lesson51 = WorkEntity("51", TYPE_CONTENT, number = "5.1", content = "Testing Basics")
//        val lesson52 = WorkEntity("52", TYPE_CONTENT, number = "5.2", content = "Dependency Injection and Test Doubles")
//        val lesson53 = WorkEntity("53", TYPE_CONTENT, number = "5.3", content = "Survey of Testing Topics [TBD]")
//        mDataEntities.add(lesson51)
//        mDataEntities.add(lesson52)
//
//        val lesson6 = WorkEntity("6", TYPE_TITLE, title = "Lesson 6: Login")
//        mDataEntities.add(lesson6)
//        val lesson61 = WorkEntity("61", TYPE_CONTENT, number = "6.1", content = "Android Login with FirebaseUI")
//        val lesson62 = WorkEntity("62", TYPE_CONTENT, number = "6.2", content = "Android Conditional Navigation with Login")
//        mDataEntities.add(lesson61)
//        mDataEntities.add(lesson62)

    }

    override fun initView() {
        super.initView()
        mAdapter.setItemClickListener(this)
        setDefaultTitle("CodeLabs")
    }

    override fun getAdapter() = CodeLabsAdapter(mContext, mDataEntities)

    override fun accept(t: WorkEntity) {
        t.clazz?.apply {
            startFragment(this)
        }
    }
}



