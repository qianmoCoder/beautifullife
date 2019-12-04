package com.ddu.ui.activity.service

import android.os.Bundle
import android.os.Message
import com.ddu.R
import com.ddu.icore.aidl.GodIntent
import com.ddu.icore.aidl.ICoreMessengerServiceConnection
import com.ddu.icore.aidl.ICoreServiceConnection
import com.ddu.icore.common.ObserverManager
import com.ddu.icore.logic.Actions
import com.ddu.icore.ui.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_service.*
import java.lang.StringBuilder

/**
 * Created by yzbzz on 2018/1/17.
 */
class MyServiceActivity : BaseActivity() {

    val sb = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
        ICoreServiceConnection.bindICoreService()
        ICoreMessengerServiceConnection.bindICoreService()
        initView()
    }

    override fun registerObserver() {
        super.registerObserver()
        ObserverManager.registerObserver(Actions.RECEIVE_SERVICE_MSG_ACTION, this)
    }

    override fun onReceiverNotify(godIntent: GodIntent) {
        when(godIntent.action) {
            Actions.RECEIVE_SERVICE_MSG_ACTION -> {
                val msg = godIntent.getString("service_msg","")
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
            ICoreServiceConnection.sendMessage(msg)
        }
        btn_send_message_messenger.setOnClickListener {
            val txt = et_msg.text.toString()
            setText(txt)
            val msg = Message.obtain()
            msg.data.putString("client_msg", txt)
            ICoreMessengerServiceConnection.sendMessage(msg)
        }
    }

    private fun setText(txt: String) {
        sb.append(txt)
        sb.append("\n")
        tv_im.text = sb.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        ICoreServiceConnection.unBindICoreService()
        ICoreMessengerServiceConnection.unBindICoreService()
    }

}