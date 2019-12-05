package com.ddu.icore.aidl

import android.os.Message
import com.ddu.icore.common.ObserverManager


/**
 * Created by yzbzz on 2019/12/2.
 */
object ICoreIPCServiceManager {

    // 发送服务端消息
    private const val SEND_SERVICE_MSG_ACTION = "send_service_msg_action"
    // 关闭Service进程
    private const val KILL_SERVICE_PROCESS = "kill_service_process"
    // 重发所有消息
    private const val RE_SEND_ALL_SERVICE_MSG = "re_send_all_service_msg"

    fun sendMessage(message: Message) {
        val godIntent = GodIntent()
        godIntent.action = SEND_SERVICE_MSG_ACTION
        godIntent.message = message
        ObserverManager.notify(godIntent)
    }

    fun sendMessage(godIntent: GodIntent) {
        godIntent.action = SEND_SERVICE_MSG_ACTION
        ObserverManager.notify(godIntent)
    }

    fun killService() {
        ObserverManager.notify(KILL_SERVICE_PROCESS)
    }

    fun reSendAllMessage() {
        ObserverManager.notify(RE_SEND_ALL_SERVICE_MSG)
    }
}