package com.ddu.ui.activity.service

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.text.TextUtils
import com.ddu.R
import com.ddu.icore.aidl.GodIntent
import com.ddu.icore.aidl.ICoreIPCServiceManager
import com.ddu.icore.common.ObserverManager
import com.ddu.icore.logic.Actions
import com.ddu.icore.ui.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_service.*

/**
 * Created by yzbzz on 2018/1/17.
 */
class MyServiceActivity : BaseActivity() {

    val sb = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
        initView()
        setDefaultTitle("服务端")
    }

    override fun registerObserver() {
        super.registerObserver()
        ObserverManager.registerObserver(Actions.CLIENT_MSG_ACTION, this)
    }

    override fun onReceiverNotify(godIntent: GodIntent) {
        when (godIntent.action) {
            Actions.CLIENT_MSG_ACTION -> {
                val msg = godIntent.getString(Actions.REPLY_SERVICE_MSG, "")
                setText(msg)
            }
        }
    }

    private fun initView() {
        btn_send_message.setOnClickListener {
            val txt = et_msg.text.toString()
            setText(txt)
            val msg = Message.obtain()
            msg.data.putString("service_msg", txt)
            msg.data.putString(Actions.REPLY_SERVICE_MSG, "client_msg: $txt")
            ICoreIPCServiceManager.sendMessage(msg)
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

    companion object {

        fun getIntent(ctx: Context): Intent {
            return Intent(ctx, MyServiceActivity::class.java)
        }
    }
}