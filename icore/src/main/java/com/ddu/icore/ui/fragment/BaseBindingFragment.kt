package com.ddu.icore.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.ddu.icore.R
import com.ddu.icore.aidl.GodIntent
import com.ddu.icore.common.IObserver
import com.ddu.icore.common.ObserverManager
import org.jetbrains.anko.support.v4.ctx


abstract class BaseBindingFragment : Fragment(), IObserver<GodIntent> {

    private var mRootView: View? = null

    private var layout: FrameLayout? = null

    val isAddFragmentCallback: Boolean
        get() = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData(savedInstanceState)
    }

    open fun isShowTitleBar() = true

    open fun isShowActivityTitleBar() = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (null == mRootView) {
            if (userVisibleHint) {
                onDataLoad()
            } else {
                layout = FrameLayout(ctx)
                layout?.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                val view = LayoutInflater.from(ctx).inflate(R.layout.fragment_lazy_loading, null)
                layout?.addView(view)
            }
            mRootView = getContentView(inflater, container, savedInstanceState)
        }
        return mRootView
    }


    open fun initData(savedInstanceState: Bundle?) {

    }

    abstract fun getContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?

    override fun onDestroyView() {
        super.onDestroyView()
        val parent = mRootView!!.parent as ViewGroup
        parent.removeView(mRootView)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregister()
    }

    private fun onDataLoad() {
    }

    override fun registerObserver() {

    }

    override fun onReceiverNotify(godIntent: GodIntent) {

    }

    fun register(action: Int) {
        ObserverManager.getInstance().registerObserver(action, this)
    }

    fun unregister() {
        ObserverManager.getInstance().unRegisterObserver(this)
    }

}