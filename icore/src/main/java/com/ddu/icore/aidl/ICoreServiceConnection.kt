package com.ddu.icore.aidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.ddu.icore.ICore

class ICoreServiceConnection private constructor() : ServiceConnection {

    private var isBind = false

    private var mICoreAidlInterface: ICoreAidlInterface? = null
    private var mServiceCallBack: ServiceCallBack? = null

    private object SingletonHolder {
        val instance = ICoreServiceConnection()
    }

    private fun bindService() {
        val service = Intent(ICore.context, ICoreMessengerService::class.java)
        isBind = ICore.context.bindService(service, this, Context.BIND_AUTO_CREATE)
        if (null != mServiceCallBack) {
            mServiceCallBack = null
        }

        mServiceCallBack = ServiceCallBack()
    }

    private fun unBindService() {
        mICoreAidlInterface?.unregisterListener(mServiceCallBack)
        ICore.context.unbindService(this)
    }

    override fun onServiceConnected(name: ComponentName, service: IBinder) {
        mICoreAidlInterface = ICoreAidlInterface.Stub.asInterface(service)
        mICoreAidlInterface?.registerListener(mServiceCallBack)

        mICoreAidlInterface?.asBinder()?.linkToDeath(mDeathRecipient, DEATH_RECIPIENT_FLAGS)
    }

    private val mDeathRecipient = object : IBinder.DeathRecipient {
        override fun binderDied() {
            mICoreAidlInterface?.unregisterListener(mServiceCallBack)
            mICoreAidlInterface?.asBinder()?.unlinkToDeath(this,DEATH_RECIPIENT_FLAGS)
            mICoreAidlInterface = null
            mServiceCallBack = null
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {

    }

    private inner class ServiceCallBack : ICoreAidlCallBackInterface.Stub() {

        override fun callback(godIntent: GodIntent?) {

        }
    }

    companion object {
        private const val DEATH_RECIPIENT_FLAGS = 0
    }

}
