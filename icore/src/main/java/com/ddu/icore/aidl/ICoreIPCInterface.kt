package com.ddu.icore.aidl

import android.os.Message

interface ICoreIPCInterface {

    fun bindICoreService()

    fun unBindICoreService()

    fun sendMessage(message: Message?)

    fun isServiceAvailable(): Boolean

    fun killICoreService()

}
