package com.ddu.icore.aidl

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger

import com.ddu.icore.ICore
import com.ddu.icore.common.ObserverManager
import com.ddu.icore.logic.Actions

import java.lang.ref.WeakReference

class ICoreServiceConnection private constructor() : ServiceConnection {

    private var isConnected = false
    private var isBind = false

    private var mMessenger: Messenger? = null
    private var mRelyMessenger: Messenger? = null
    private var mGetRelyHandler: GetRelyHandler? = null

    private var isUserCancel = false
    private var isKillService = false

    private val isCanRebind: Boolean
        get() = !isUserCancel && !isKillService

    private val mDeathRecipient = object : IBinder.DeathRecipient {
        override fun binderDied() {
            isConnected = false
            if (mMessenger == null) {
                return
            }
            mMessenger?.binder?.unlinkToDeath(this, DEATH_RECIPIENT_FLAGS)
            mMessenger = null
            // Binder死亡，重新绑定服务
            reBindService()
        }
    }

    private object SingletonHolder {
        val instance = ICoreServiceConnection()
    }

    private fun reBindService() {
        if (isCanRebind) {
            synchronized(this) {
                if (isCanRebind) {
                    bindService(false)
                }
            }
        }
    }

    private fun bindService(isFirstBind: Boolean) {
        if (isFirstBind || isCanRebind) {
            isKillService = false
            isUserCancel = false
            synchronized(this) {
                val service = Intent(ICore.app, ICoreService::class.java)
                isBind = ICore.app.bindService(service, this, Context.BIND_AUTO_CREATE)
                if (null == mGetRelyHandler) {
                    mGetRelyHandler = GetRelyHandler(this)

                    mRelyMessenger = null
                    mRelyMessenger = Messenger(mGetRelyHandler)
                }
            }
        }
    }

    private fun unBindService() {
        isUserCancel = true
        isKillService = true
        release()
        synchronized(this) {
            if (isBind) {
                ICore.app.unbindService(this)
                isBind = false
            }
            isConnected = false
        }
    }

    private fun release() {
        if (mGetRelyHandler != null) {
            mGetRelyHandler?.removeCallbacksAndMessages(null)
            mGetRelyHandler = null
        }
        if (mRelyMessenger != null) {
            mRelyMessenger = null
        }

        if (mMessenger != null) {
            mMessenger = null
        }

        MessageManager.clear()
    }

    private fun killService() {
        isKillService = true
        val message = Message.obtain()
        message.what = Actions.KILL_SERVICE
        sendMsg(message)
    }

    private fun sendMsg(message: Message?) {
        if (null != message) {
            if (null != mRelyMessenger) {
                message.replyTo = mRelyMessenger
            }
            if (isConnected && null != mMessenger) {
                try {
                    mMessenger?.send(message)
                } catch (e: Exception) {
                    e.printStackTrace()
                    enqueueMessageAndReBindService(message)
                }

            } else {
                enqueueMessageAndReBindService(message)
            }
        }
    }

    private fun enqueueMessageAndReBindService(message: Message) {
        if (!isUserCancel) {
            MessageManager.add(message)
            reBindService()
        }
    }

    private fun reSendAllMsg(messenger: Messenger?, relyMessenger: Messenger?) {
        if (isServiceAvailable && null != messenger) {
            val concurrentLinkedQueue = MessageManager.getMessages()
            val messageIterator = concurrentLinkedQueue.iterator()
            while (messageIterator.hasNext()) {
                val message = messageIterator.next()
                if (null != relyMessenger) {
                    message.replyTo = relyMessenger
                }
                try {
                    messenger.send(message)
                    messageIterator.remove()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

    private fun sendClientMessengerMessage() {
        val message = Message.obtain()
        message.data.putString(SEND_CLIENT_MESSENGER, SEND_CLIENT_MESSENGER)
        sendMsg(message)
    }

    @SuppressLint("HandlerLeak")
    private inner class GetRelyHandler(s: ICoreServiceConnection) : Handler() {

        private var serviceConnectionWeakReference = WeakReference(s)

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val godIntent = GodIntent(Actions.RECEIVE_SERVICE_MSG_ACTION, msg)

            val serviceConnection = serviceConnectionWeakReference.get()

            val what = msg.what
            if (what == Actions.KILL_SERVICE) {
                serviceConnection?.killService()
            } else {
                ObserverManager.notify(godIntent)
            }
        }
    }

    override fun onServiceConnected(name: ComponentName, service: IBinder) {
        isConnected = true
        mMessenger = Messenger(service)
        try {
            // 调用bindService，先发送一个空包，传递客户端的Messenger对象给服务端。服务端使用Messenger对象向客户端发送消息
            sendClientMessengerMessage()
            if (MessageManager.isNotEmpty()) {
                reSendAllMsg(mMessenger, mRelyMessenger)
            }
            service.linkToDeath(mDeathRecipient, DEATH_RECIPIENT_FLAGS)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            ObserverManager.notify(Actions.SERVICE_CONNECTED_ACTION)
        }
    }

    override fun onServiceDisconnected(name: ComponentName) {
        isConnected = false
        reBindService()
    }

    override fun onBindingDied(name: ComponentName) {
        isConnected = false
    }

    override fun onNullBinding(name: ComponentName) {
        isConnected = false
    }

    companion object {

        private const val SEND_CLIENT_MESSENGER = "send_client_messenger"

        private const val DEATH_RECIPIENT_FLAGS = 0

        private val instance: ICoreServiceConnection
            get() = SingletonHolder.instance

        fun bindLimiService() {
            instance.bindService(true)
        }

        fun unBindLimiService() {
            instance.unBindService()
        }

        fun sendMessage(message: Message?) {
            if (null != message) {
                instance.sendMsg(message)
            }
        }

        val isServiceAvailable: Boolean
            get() = instance.isBind && instance.isConnected

        fun killLimiService() {
            instance.killService()
        }
    }
}
