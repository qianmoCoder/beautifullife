package com.ddu.icore.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.ddu.icore.R
import com.ddu.icore.common.ext.act
import com.ddu.icore.ui.fragment.BaseBindingFragment
import com.ddu.icore.ui.widget.TitleBar

abstract class DefaultBindingFragment<T : ViewDataBinding> : BaseBindingFragment() {

    protected lateinit var mView: View
    var titleBar: TitleBar? = null
        protected set
    protected var mCustomerTitleBar: View? = null

    abstract fun getLayoutId(): Int

    lateinit var dataBinding: T

    override fun getContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layoutId = getLayoutId()
        if (isShowTitleBar()) {
            dataBinding = DataBindingUtil.inflate(inflater, R.layout.i_activity_base, container, false)
            mView = dataBinding.root
            titleBar = mView.findViewById(R.id.ll_title_bar)
            inflater.inflate(layoutId, mView as ViewGroup, true)
        } else {
            dataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
            mView = dataBinding.root
        }

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    abstract fun initView()

    fun setTitle(resId: Int) {
        if (null != titleBar) {
            titleBar!!.setMiddleText(resId)
        }
    }

    fun setTitle(title: String) {
        if (null != titleBar) {
            titleBar!!.setMiddleText(title)
        }
    }

    fun setDefaultTitle(resId: Int) {
        if (null != titleBar) {
            titleBar!!.setDefaultTitle(resId) { act.onBackPressed() }
        }
    }

    fun setDefaultTitle(title: String) {
        if (null != titleBar) {
            titleBar!!.setDefaultTitle(title) { act.onBackPressed() }
        }
    }

    fun setRightText(text: String, onClickListener: View.OnClickListener) {
        if (null != titleBar) {
            titleBar!!.setRightText(text, onClickListener)
        }
    }

    fun setRightImg(resId: Int, onClickListener: View.OnClickListener) {
        if (null != titleBar) {
            titleBar!!.setRightImg(resId, onClickListener)
        }
    }

    fun setTitleBarOnClickListener(onClickListener: View.OnClickListener) {
        if (null != titleBar) {
            titleBar!!.setOnClickListener(onClickListener)
        }
    }

    companion object {

        const val ARGUMENT_TASK_ID = "ARGUMENT_TASK_ID"
    }

}
