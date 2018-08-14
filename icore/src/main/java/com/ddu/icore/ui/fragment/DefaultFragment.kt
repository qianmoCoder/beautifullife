package com.ddu.icore.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ddu.icore.R
import com.ddu.icore.ui.activity.BaseActivity
import com.ddu.icore.ui.activity.ShowDetailActivity
import com.ddu.icore.ui.view.TitleBar
import com.ddu.icore.util.FragmentUtils

abstract class DefaultFragment : BaseFragment() {

    protected lateinit var baseActivity: BaseActivity
    protected lateinit var mView: View
    var titleBar: TitleBar? = null
        protected set
    protected var mCustomerTitleBar: View? = null

    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseActivity = mActivity as BaseActivity
    }

    override fun getContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (isShowTitleBar()) {
            mView = inflater.inflate(R.layout.activity_base, container, false)
            titleBar = mView.findViewById(R.id.ll_title_bar)
            inflater.inflate(getLayoutId(), mView as ViewGroup, true)
        } else {
            mView = inflater.inflate(getLayoutId(), container, false)
        }
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (userDefaultTitle()) {
            val title = arguments?.getString("title", "")
            if (!title.isNullOrEmpty()) {
                setDefaultTitle(title!!)
            }
        }
        initView()
    }

    open fun userDefaultTitle() = true

    abstract fun initView()

    fun startFragment(className: Class<out Fragment>) {
        baseActivity?.startFragment(className)
    }

    fun startFragment(className: String) {
        baseActivity?.startFragment(className)
    }

    fun startFragment(className: String, bundle: Bundle) {
        baseActivity?.startFragment(className, bundle)
    }

    fun startFragment(className: Class<out Fragment>, bundle: Bundle) {
        baseActivity?.startFragment(className, bundle)
    }

    fun replaceFragment(fragment: Fragment) {
        if (mActivity is ShowDetailActivity) {
            (mActivity as ShowDetailActivity).replaceFragment(fragment, FragmentUtils.FRAGMENT_ADD_TO_BACK_STACK)
        }
    }

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
            titleBar!!.setDefaultTitle(resId) { baseActivity?.onBackPressed() }
        }
    }

    fun setDefaultTitle(title: String) {
        if (null != titleBar) {
            titleBar!!.setDefaultTitle(title) { baseActivity?.onBackPressed() }
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

    fun <T : View> findViewById(resId: Int): T {
        return mView.findViewById(resId)
    }

    companion object {

        val ARGUMENT_TASK_ID = "ARGUMENT_TASK_ID"
    }
}
