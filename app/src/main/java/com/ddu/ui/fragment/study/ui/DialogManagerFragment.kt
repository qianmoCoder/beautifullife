package com.ddu.ui.fragment.study.ui

import android.os.Bundle
import com.ddu.R
import com.ddu.app.BaseApp
import com.ddu.icore.common.ext.act
import com.ddu.icore.dialog.AlertDialogFragment
import com.ddu.icore.ui.fragment.DefaultFragment
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_dialog_manager.*

/**
 * Created by yzbzz on 2017/5/19.
 */
@IElement("UI")
class DialogManagerFragment : DefaultFragment() {

    /**
     * 活动弹窗的优先级
     */
    val AD_PRIORITY = 1
    /**
     * 更新弹窗的优先级
     */
    val UPDATE_PRIORITY = 2
    /**
     * alert弹窗的优先级
     */
    val ALERT_PRIORITY = 3
    /**
     * 登录弹窗的优先级
     */
    val LOGIN_PRIORITY = 4
    /**
     * other弹窗的优先级
     */
    val OTHER_PRIORITY = 5

    override fun initData(savedInstanceState: Bundle?) {

        list.add(AD_PRIORITY)
        list.add(ALERT_PRIORITY)

        BaseApp.postDelayed(Runnable {
            list.add(UPDATE_PRIORITY)
        }, 2000)

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_dialog_manager
    }

    override fun initView() {
        btn_start.setOnClickListener {
            getQueue()
        }
    }

    companion object {

        var list = mutableListOf<Int>()
    }

    fun getQueue() {
        list.sort()
        if (list.size > 0) {
            showDialog(list.removeAt(0))
        }
    }

    fun showDialog(level: Int) {
        when (level) {
            AD_PRIORITY -> showDialog(level,"广告")
            UPDATE_PRIORITY -> showDialog(level, "更新")
            ALERT_PRIORITY -> showDialog(level, "弹窗")
        }

    }

    fun showDialog(level: Int, titleMsg: String) {
        val dialog = AlertDialogFragment().apply {
            title = titleMsg
            msg = "逗你玩"
            leftText = "取消"
            rightText = "确定"
            mLeftClickListener = { _, _ ->
                dismissAllowingStateLoss()
                getQueue()
            }
            mRightClickListener = { _, _ ->
                dismissAllowingStateLoss()
                getQueue()
            }
        }
        val transaction = act.supportFragmentManager.beginTransaction()
        transaction.add(dialog, "dialog")
        transaction.commitAllowingStateLoss()
        list.remove(level)
    }
}
