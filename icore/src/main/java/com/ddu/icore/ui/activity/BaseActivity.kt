package com.ddu.icore.ui.activity

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import android.view.*
import com.ddu.icore.R
import com.ddu.icore.aidl.GodIntent
import com.ddu.icore.common.IObserver
import com.ddu.icore.common.ObserverManager
import com.ddu.icore.navigation.Navigator
import com.ddu.icore.ui.view.TitleBar
import com.ddu.icore.util.sys.ViewUtils

abstract class BaseActivity : AppCompatActivity(), IObserver<GodIntent> {

    lateinit var mContext: Context
    lateinit var mApplication: Application
    lateinit var mApplicationContext: Context

    lateinit var mViewGroup: ViewGroup

    var titleBar: TitleBar? = null
        protected set

    var fragmentCallback: OnFragmentCallback? = null

    lateinit var mLayoutInflater: LayoutInflater

    protected var mCustomerTitleBar: View? = null

    private val appCompatDelegate: AppCompatDelegate? = null

    open fun isShowTitleBar() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(null)
        mContext = this
        mApplication = application
        mApplicationContext = applicationContext
        mLayoutInflater = LayoutInflater.from(mContext)
        registerObserver()
        //        Log.v("lhz", getClass().getName() + " onCreate");
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        if (isShowTitleBar()) {
            super.setContentView(R.layout.activity_base)
            mViewGroup = findView(R.id.ll_activity_base)
            titleBar = mViewGroup.findViewById<View>(R.id.ll_title_bar) as TitleBar
            layoutInflater.inflate(layoutResID, mViewGroup)
        } else {
            super.setContentView(layoutResID)
        }

    }

    fun startFragment(fragment: Class<out Fragment>) {
        startFragment(fragment, null)
    }

    fun startFragment(fragmentName: String) {
        Navigator.startShowDetailActivity(this, fragmentName, null)
    }

    fun startFragment(fragment: Class<out Fragment>, bundle: Bundle?) {
        Navigator.startShowDetailActivity(this, fragment, bundle)
    }

    fun <T : View> findView(resId: Int): T {
        return ViewUtils.findViewById(this, resId)
    }

    override fun registerObserver() {

    }

    override fun onReceiverNotify(godIntent: GodIntent) {

    }

    override fun onDestroy() {
        super.onDestroy()
        ObserverManager.getInstance().unRegisterObserver(this)
    }

    fun setDefaultTitle(resId: Int) {
        if (null != titleBar) {
            titleBar!!.setMiddleText(resId)
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
        Log.v("lhz", "dispatchTouchEvent: " + ev.x + " " + ev.y)
        return super.dispatchTouchEvent(ev)
    }
}
