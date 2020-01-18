package com.ddu.ui.fragment.work.kotlin.motionlayout

import android.os.Bundle
import android.view.View
import com.ddu.icore.ui.fragment.AbsRVFragment
import com.ddu.ui.fragment.WebFragment
import com.iannotation.ICodeLabsElement

data class Step(
        val number: String,
        val name: String,
        val caption: String,
        val clazz: Class<*>,
        val highlight: Boolean = false,
        val classType: String? = "0")

private val data = listOf(
        Step("Step 1",
                "Animations with Motion Layout",
                "Learn how to build a basic animation with Motion Layout. This will crash until you complete the step in the codelab.",
                Step1Fragment::class.java
        ),
        Step("Step 2",
                "Animating based on drag events",
                "Learn how to control animations with drag events. This will not display any animation until you complete the step in the codelab.",
                Step2Fragment::class.java
        ),
        Step("Step 3",
                "Modifying a path",
                "Learn how to use KeyFrames to modify a path between start and end.",
                Step3Fragment::class.java
        ),
        Step("Step 4",
                "Building complex paths",
                "Learn how to use KeyFrames to build complex paths through multiple KeyFrames.",
                Step4Fragment::class.java
        ),
        Step("Step 5",
                "Changing attributes with motion",
                "Learn how to resize and rotate views during animations.",
                Step5Fragment::class.java
        ),
        Step("Step 6",
                "Changing custom attributes",
                "Learn how to change custom attributes during motion.",
                Step6Fragment::class.java
        ),
        Step("Step 7",
                "OnSwipe with complex paths",
                "Learn how to control motion through complex paths with OnSwipe.",
                Step7Fragment::class.java
        ),
        Step("Completed: Steps 2-7",
                "Steps 2-7 completed",
                "All changes in steps 2-7 applied",
                Step7CompletedFragment::class.java,
                highlight = true
        ),
        Step("Step 8",
                "Running motion with code",
                "Learn how to use MotionLayout to build complex collapsing toolbar animations.",
                Step8Activity::class.java,
                classType = "1"
        ),
        Step("Completed: Step 8 ",
                "Implements running motion with code",
                "Changes applied from step 8",
                Step8CompletedActivity::class.java,
                highlight = true,
                classType = "1"
        )
)


/**
 * Created by yzbzz on 16/4/8.
 */
@ICodeLabsElement(path = "Kotlin_CodeLabs", parentId = "3", parentContent = "Animation",
        id = "2", content = "Animation with MotionLayout")
class MotionLayoutFragment : AbsRVFragment<Step, MotionLayoutAdapter>() {

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        mDataEntities.addAll(data)
    }

    override fun initView() {
        super.initView()
        mRvDefault.clipToPadding = true
        setDefaultTitle("MotionLayoutFragment")
        setRightText("地址", View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("title", "Animation with MotionLayout")
            bundle.putString("url", "https://codelabs.developers.google.com/codelabs/motion-layout/#0")
            startFragment(WebFragment::class.java, bundle)
        })
    }

    override fun getAdapter() = MotionLayoutAdapter(mContext, mDataEntities)
}
