package com.ddu.icore.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Parcel
import android.os.RemoteCallbackList

/**
 * Created by yzbzz on 2019/12/2.
 */
class ICoreService : Service() {

    private val mRemoteCallbackList = RemoteCallbackList<ICoreAidlCallBackInterface>()


    override fun onBind(intent: Intent?): IBinder? {
        return iBinder
    }

    private val iBinder = object : ICoreAidlInterface.Stub() {

        override fun sendMessage(godIntent: GodIntent?) {
            mRemoteCallbackList.beginBroadcast()
            sendMessageToAllClient(godIntent)
            mRemoteCallbackList.finishBroadcast()
        }

        override fun registerListener(listener: ICoreAidlCallBackInterface) {
            try {
                mRemoteCallbackList.register(listener)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        override fun unregisterListener(listener: ICoreAidlCallBackInterface?) {
            try {
                mRemoteCallbackList.unregister(listener)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        //这里用于捕获里面出现的异常，防止服务端有错误的异常直接抛向客户端
        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            try {
                return super.onTransact(code, data, reply, flags)
            } catch (e: Exception) {
                throw  e
            }
        }

        private fun sendMessageToAllClient(godIntent: GodIntent?) {
            val count = mRemoteCallbackList.registeredCallbackCount
            for (i in 0 until count) {
                try {
                    mRemoteCallbackList.getBroadcastItem(i).callback(godIntent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    }

}