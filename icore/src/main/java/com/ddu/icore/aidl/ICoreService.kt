package com.ddu.icore.aidl

import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import android.text.TextUtils
import com.ddu.icore.common.IObserver
import com.ddu.icore.common.ObserverManager
import com.ddu.icore.logic.Actions
import com.ddu.icore.logic.LogicManager
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.system.exitProcess

/**
 * Created by yzbzz on 2019/12/2.
 */
class ICoreService : Service(), IObserver {

    private var mRemoteCallbackList: RemoteCallbackList<ICoreAidlCallBackInterface>? = null

    private var mLogicManager: LogicManager? = null

    private val isReSendAllMsgSuccess = AtomicBoolean(true)

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        registerObserver()
        mRemoteCallbackList = RemoteCallbackList()
        mLogicManager = LogicManager.getInstance()
        mLogicManager?.initAllLogic()
    }

    fun registerObserver() {
        ObserverManager.registerObserver(SEND_SERVICE_MSG_ACTION, this)
        ObserverManager.registerObserver(KILL_SERVICE_PROCESS, this)
        ObserverManager.registerObserver(RE_SEND_ALL_SERVICE_MSG, this)
    }

    override fun onReceiverNotify(godIntent: GodIntent) {
        when (godIntent.action) {
            SEND_SERVICE_MSG_ACTION -> {
                val message = godIntent.message
                sendServiceMessage(message)
            }
            KILL_SERVICE_PROCESS -> killSelf()
            RE_SEND_ALL_SERVICE_MSG -> reSendAllMsg()
        }
    }

    private fun killSelf() {
        stopSelf()
        Process.killProcess(Process.myPid())
        exitProcess(0)
    }

    override fun onDestroy() {
        super.onDestroy()
        release()
    }

    private fun release() {
        ObserverManager.unRegisterObserver(this)

        if (null != mRemoteCallbackList) {
            mRemoteCallbackList = null
        }

        if (null != mLogicManager) {
            mLogicManager?.releaseAllLogic()
        }

        MessageManager.clear()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return if (checkCallingOrSelfPermission("com.icore.permission.ACCESS_SERVICE") == PackageManager.PERMISSION_DENIED) {
            null
        } else {
            iBinder
        }
    }

    private val iBinder = object : ICoreAidlInterface.Stub() {

        override fun sendMessage(godIntent: GodIntent?) {
            godIntent?.let {
                val replyToMsg = it.getString(Actions.REPLY_TO_MSG, "")
                if (!TextUtils.isEmpty(replyToMsg)) {
                    val replyToMessage = Message.obtain()
                    replyToMessage.data = it.data
                    sendServiceMessage(replyToMessage)
                }

                notifyListener(godIntent)
            }
        }

        override fun registerListener(listener: ICoreAidlCallBackInterface) {
            mRemoteCallbackList?.register(listener)
        }

        override fun unregisterListener(listener: ICoreAidlCallBackInterface?) {
            mRemoteCallbackList?.unregister(listener)

        }

        //这里用于捕获里面出现的异常，防止服务端有错误的异常直接抛向客户端
        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            try {
                return super.onTransact(code, data, reply, flags)
            } catch (e: Exception) {
                throw  e
            }
        }
    }

    private fun sendMessageToAllClient(godIntent: GodIntent?) {
        mRemoteCallbackList?.let {
            it.beginBroadcast()
            val count = it.registeredCallbackCount
            for (i in 0 until count) {
                it.getBroadcastItem(i).callback(godIntent)
            }
            it.finishBroadcast()
        }
    }

    private fun notifyListener(godIntent: GodIntent) {
        if (null == mLogicManager) {
            mLogicManager = LogicManager.getInstance()
        }

        mLogicManager?.notifyListener(godIntent)
    }

    private fun sendServiceMessage(message: Message?) {
        if (null != message) {
            try {
                val godIntent = GodIntent(Actions.SERVICE_MSG_ACTION, message)
                sendMessageToAllClient(godIntent)
            } catch (e: Exception) {
                MessageManager.add(message)
            }
        }
    }

    private fun reSendAllMsg() {
        if (isReSendAllMsgSuccess.compareAndSet(true, true)) {
            isReSendAllMsgSuccess.set(false)
            if (MessageManager.isNotEmpty()) {
                val concurrentLinkedQueue = MessageManager.getMessages()
                val messageIterator = concurrentLinkedQueue.iterator()
                while (messageIterator.hasNext()) {
                    val message = messageIterator.next()
                    try {
                        val godIntent = GodIntent(Actions.SERVICE_MSG_ACTION, message)
                        sendMessageToAllClient(godIntent)
                        messageIterator.remove()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }
            isReSendAllMsgSuccess.set(true)
        }
    }

    companion object {

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

}