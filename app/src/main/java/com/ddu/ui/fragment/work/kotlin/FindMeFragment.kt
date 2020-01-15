package com.ddu.ui.fragment.work.kotlin

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.ui.fragment.WebFragment
import com.iannotation.ICodeLabsElement

/**
 * Created by yzbzz on 2018/6/8.
 */
@ICodeLabsElement(path = "Kotlin_CodeLabs", parentId = "2", parentContent = "Advanced Graphics",
        id = "4", content = "Creating Effects with Shaders")
class FindMeFragment : DefaultFragment() {

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        val dialog = createInstructionsDialog()
        dialog.show()
    }

    override fun getLayoutId(): Int {
        return R.layout.study_customer_find_me
    }

    override fun initView() {
        setDefaultTitle("FindMe")
        setRightText("地址", View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("title", "Creating Effects with Shaders")
            bundle.putString("url", "https://codelabs.developers.google.com/codelabs/advanced-android-kotlin-training-shaders/#0")
            startFragment(WebFragment::class.java, bundle)
        })
    }

    private fun createInstructionsDialog(): Dialog {
        val builder = AlertDialog.Builder(mContext)
        builder.setIcon(R.drawable.icon_find_me_android)
                .setTitle(R.string.instructions_title)
                .setMessage(R.string.instructions)
                .setPositiveButtonIcon(ContextCompat.getDrawable(mContext, android.R.drawable.ic_media_play))
        return builder.create()
    }

}
