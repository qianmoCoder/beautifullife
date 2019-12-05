package com.ddu.ui.activity.service

import android.os.Bundle
import android.os.Message
import android.text.TextUtils
import com.ddu.R
import com.ddu.icore.aidl.GodIntent
import com.ddu.icore.aidl.ICoreIPCClientManager
import com.ddu.icore.common.ObserverManager
import com.ddu.icore.logic.Actions
import com.ddu.icore.ui.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_client.*

/**
 * Created by yzbzz on 2018/1/17.
 */
class MyClientActivity : BaseActivity() {

    val sb = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)
        initView()
        setDefaultTitle("客户端")
        ICoreIPCClientManager.getIPC().bindICoreService()
    }

    override fun registerObserver() {
        super.registerObserver()
        ObserverManager.registerObserver(Actions.SERVICE_MSG_ACTION, this)
    }

    override fun onReceiverNotify(godIntent: GodIntent) {
        when (godIntent.action) {
            Actions.SERVICE_MSG_ACTION -> {
                val msg = godIntent.getString(Actions.REPLY_CLIENT_MSG, "")
                setText(msg)
            }
        }
    }

    private fun initView() {
        btn_send_message.setOnClickListener {
            val txt = et_msg.text.toString()
            setText(txt)
            val msg = Message.obtain()
            msg.data.putString("client_msg", txt)
            msg.data.putString(Actions.REPLY_CLIENT_MSG, "${ICoreIPCClientManager.getIPCName()}: $txt")
            ICoreIPCClientManager.getIPC().sendMessage(msg)
        }
        btn_switch.setOnClickListener {
            ICoreIPCClientManager.getIPC().unBindICoreService()
            ICoreIPCClientManager.switchIPC()
            ICoreIPCClientManager.getIPC().bindICoreService()
        }
        btn_start_service_activity.setOnClickListener {
            startActivity(MyServiceActivity.getIntent(this))
        }
    }

    private fun setText(txt: String) {
        if (TextUtils.isEmpty(txt)) {
            return
        }
        runOnUiThread(Runnable {
            sb.append(txt)
            sb.append("\n")
            tv_im.text = sb.toString()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        ICoreIPCClientManager.getIPC().unBindICoreService()
    }

}