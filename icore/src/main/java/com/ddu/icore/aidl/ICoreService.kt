package com.ddu.icore.aidl

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import android.text.TextUtils
import com.ddu.icore.common.IObserver
import com.ddu.icore.common.ObserverManager
import com.ddu.icore.logic.Actions
import com.ddu.icore.logic.LogicManager
import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.system.exitProcess

/**
 * Created by yzbzz on 2019-10-12.
 */
class ICoreService : Service(), IObserver {

    private var mMessengerHandler: MessengerHandler? = null
    private var mMessenger: Messenger? = null
    private var mClientMessenger: Messenger? = null

    private var mLogicManager: LogicManager? = null

    private val isReSendAllMsgSuccess = AtomicBoolean(true)

    override fun onCreate() {
        super.onCreate()
        init()
    }

    fun registerObserver() {
        ObserverManager.registerObserver(Actions.SEND_SERVICE_MSG_ACTION, this)
        ObserverManager.registerObserver(Actions.KILL_SERVICE_PROCESS, this)
        ObserverManager.registerObserver(Actions.RE_SEND_ALL_SERVICE_MSG, this)
    }

    override fun onReceiverNotify(godIntent: GodIntent) {
        when (godIntent.action) {
            Actions.SEND_SERVICE_MSG_ACTION -> {
                val message = godIntent.message
                sendServiceMessage(message)
            }
            Actions.KILL_SERVICE_PROCESS -> killSelf()
            Actions.RE_SEND_ALL_SERVICE_MSG -> reSendAllMsg(mClientMessenger)
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

    private fun init() {
        registerObserver()
        mMessengerHandler = MessengerHandler(this)
        mMessenger = Messenger(mMessengerHandler)

        mLogicManager = LogicManager.getInstance()
        mLogicManager?.initAllLogic()
    }

    private fun release() {
        ObserverManager.unRegisterObserver(this)

        if (null != mMessengerHandler) {
            mMessengerHandler?.removeCallbacksAndMessages(null)
            mMessengerHandler = null
        }
        if (null != mMessenger) {
            mMessenger = null
        }

        if (null != mClientMessenger) {
            mClientMessenger = null
        }
        if (null != mLogicManager) {
            mLogicManager?.releaseAllLogic()
        }

        MessageManager.clear()
    }

    override fun onBind(intent: Intent): IBinder? {
        return if (checkCallingOrSelfPermission("com.icore.permission.ACCESS_LIVE_SERVICE") == PackageManager.PERMISSION_DENIED) {
            null
        } else mMessenger?.binder
    }


    @SuppressLint("HandlerLeak")
    private inner class MessengerHandler(s: ICoreService) : Handler() {

        private var serviceWeakReference: WeakReference<ICoreService> = WeakReference(s)

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            val clientMessenger = msg.replyTo
            val bundle = msg.data


            val limiService = serviceWeakReference.get()

            val what = msg.what
            if (what == Actions.KILL_SERVICE) {
                limiService?.killSelf()
                return
            }

            if (null != limiService) {
                limiService.setClientMessenger(clientMessenger)
                //取出客户端的消息内容
                val godIntent = GodIntent(Actions.RECEIVE_CLIENT_MSG_ACTION, msg)
                if (null != bundle) {
                    val value = bundle.getString(SEND_CLIENT_MESSENGER, "")
                    if (SEND_CLIENT_MESSENGER.equals(value, ignoreCase = true)) {
                        limiService.reSendAllMsg(clientMessenger)
                    } else {
                        limiService.notifyListener(godIntent)
                    }
                } else {
                    limiService.notifyListener(godIntent)
                }
            }

            //新建一个Message对象，作为回复客户端的对象
            if (null != bundle) {
                val replyToMsg = bundle.getString(Actions.REPLY_TO_MSG, "")
                if (!TextUtils.isEmpty(replyToMsg)) {
                    val replyToMessage = Message.obtain()
                    replyToMessage.data = bundle
                    try {
                        clientMessenger?.send(replyToMessage)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }
        }
    }

    private fun setClientMessenger(clientMessenger: Messenger?) {
        if (null == mClientMessenger || mClientMessenger !== clientMessenger) {
            mClientMessenger = clientMessenger
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
                if (mClientMessenger != null) {
                    mClientMessenger?.send(message)
                } else {
                    MessageManager.add(message)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun reSendAllMsg(messenger: Messenger?) {
        if (isReSendAllMsgSuccess.compareAndSet(true, true)) {
            isReSendAllMsgSuccess.set(false)
            if (null != messenger && MessageManager.isNotEmpty()) {
                val concurrentLinkedQueue = MessageManager.getMessages()
                val messageIterator = concurrentLinkedQueue.iterator()
                while (messageIterator.hasNext()) {
                    val message = messageIterator.next()
                    try {
                        messenger.send(message)
                        messageIterator.remove()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }
            isReSendAllMsgSuccess.set(true)
        }
    }

    fun reSendAllMessage() {
        ObserverManager.notify(Actions.RE_SEND_ALL_SERVICE_MSG)
    }

    companion object {

        private const val SEND_CLIENT_MESSENGER = "send_client_messenger"

        fun sendMessage(message: Message) {
            val godIntent = GodIntent()
            godIntent.action = Actions.SEND_SERVICE_MSG_ACTION
            godIntent.message = message
            ObserverManager.notify(godIntent)
        }

        fun sendMessage(godIntent: GodIntent) {
            godIntent.action = Actions.SEND_SERVICE_MSG_ACTION
            ObserverManager.notify(godIntent)
        }

        fun killService() {
            val godIntent = GodIntent()
            godIntent.action = Actions.SEND_SERVICE_MSG_ACTION
            godIntent.setWhat(Actions.KILL_SERVICE)
            ObserverManager.notify(godIntent)
        }
    }
}
