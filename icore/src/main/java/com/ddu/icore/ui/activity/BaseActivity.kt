package com.ddu.icore.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.ddu.icore.R
import com.ddu.icore.aidl.GodIntent
import com.ddu.icore.common.IObserver
import com.ddu.icore.common.ObserverManager
import com.ddu.icore.common.ext.find
import com.ddu.icore.navigation.Navigator
import com.ddu.icore.ui.widget.TitleBar
import com.gyf.immersionbar.ImmersionBar

open class BaseActivity : AppCompatActivity(),
    IObserver {

    lateinit var mContext: Context

    lateinit var mViewGroup: ViewGroup

    lateinit var mImmersionBar: ImmersionBar

    var titleBar: TitleBar? = null
        protected set

    var fragmentCallback: OnFragmentCallback? = null

    protected var mCustomerTitleBar: View? = null

    private val appCompatDelegate: AppCompatDelegate? = null

    open fun isShowTitleBar() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(null)
        mContext = this
        registerObserver()

//        mImmersionBar = ImmersionBar.with(this)
//        mImmersionBar.statusBarDarkFont(true,0.2f).statusBarColor(R.color.c_ffffff).fitsSystemWindows(true).init()
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        if (isShowTitleBar()) {
            super.setContentView(R.layout.i_activity_base)
            mViewGroup = find(R.id.ll_activity_base)
            titleBar = find(R.id.ll_title_bar)
            layoutInflater.inflate(layoutResID, mViewGroup)
        } else {
            super.setContentView(layoutResID)
        }

    }

    fun startFragment(fragment: Class<out androidx.fragment.app.Fragment>) {
        startFragment(fragment, null)
    }

    fun startFragment(fragmentName: String) {
        Navigator.startShowDetailActivity(this, fragmentName, null)
    }

    fun startFragment(fragmentName: String, bundle: Bundle?) {
        Navigator.startShowDetailActivity(this, fragmentName, bundle)
    }

    fun startFragment(fragment: Class<out androidx.fragment.app.Fragment>, bundle: Bundle?) {
        Navigator.startShowDetailActivity(this, fragment, bundle)
    }


    open fun registerObserver() {
    }

    override fun onReceiverNotify(godIntent: GodIntent) {

    }

    override fun onDestroy() {
        super.onDestroy()
        ObserverManager.unRegisterObserver(this)
    }

    fun setDefaultTitle(resId: Int) {
        titleBar?.let {
            it.setMiddleText(resId)
            setDefaultLeftImg()
        }
    }

    fun setDefaultTitle(title: String) {
        if (null != titleBar) {
            titleBar!!.setMiddleText(title)
            setDefaultLeftImg()
        }
    }

    fun setRightText(text: String, onClickListener: View.OnClickListener) {
        if (null != titleBar) {
            val textView = titleBar!!.rightText
            textView.text = text
            textView.setOnClickListener(onClickListener)
        }
    }

    fun setRightImg(resId: Int, onClickListener: View.OnClickListener) {
        if (null != titleBar) {
            val imageView = titleBar!!.rightImg
            imageView.setImageResource(resId)
            imageView.setOnClickListener(onClickListener)
        }
    }

    private fun setDefaultLeftImg() {
        if (null != titleBar) {
            titleBar!!.setDefaultLeftImg { onBackPressed() }
        }
    }

    interface OnFragmentCallback {
        fun onFragmentKeyDownPress(keyCode: Int, keyEvent: KeyEvent): Boolean

        fun onFragmentBackPress(): Boolean
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return super.dispatchTouchEvent(ev)
    }
}
