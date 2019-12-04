package com.ddu.icore.aidl

import androidx.annotation.IntDef


/**
 * Created by yzbzz on 2019/12/2.
 */
object ICoreIPCManager {

    private const val IPC_AIDL = 0x001
    private const val IPC_MESSENGER = 0x002

    private var IPC = IPC_AIDL

    fun getIPC(): ICoreIPCInterface = if (IPC == IPC_AIDL) {
        ICoreServiceConnection.instance
    } else {
        ICoreMessengerServiceConnection.instance
    }

    fun setIPC(@IPCAnnotation ipc: Int) {
        IPC = ipc
    }

    fun switchIPC() {
        IPC = if (IPC == IPC_AIDL) {
            IPC_MESSENGER
        } else {
            IPC_AIDL
        }
    }

    fun getIPCName(): String {
        return if (IPC == IPC_AIDL) {
            "aidl"
        } else {
            "messenger"
        }
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(IPC_AIDL, IPC_MESSENGER)
    annotation class IPCAnnotation

}