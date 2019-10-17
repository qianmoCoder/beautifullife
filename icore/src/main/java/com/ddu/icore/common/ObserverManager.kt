package com.ddu.icore.common


import com.ddu.icore.aidl.GodIntent

object ObserverManager {

    @JvmStatic
    fun registerObserver(action: String, observer: IObserver) {
        GodIntentObservable.getInstance().registerObserver(action, observer)
    }

    @JvmStatic
    fun unRegisterObserver(observer: IObserver) {
        GodIntentObservable.getInstance().unRegisterObserver(observer)
    }

    @JvmStatic
    fun unRegisterObserver(action: String, observer: IObserver) {
        GodIntentObservable.getInstance().unRegisterObserver(action, observer)
    }

    @JvmStatic
    fun notify(action: String) {
        GodIntentObservable.getInstance().notify(action)
    }

    @JvmStatic
    fun notify(godIntent: GodIntent) {
        GodIntentObservable.getInstance().notify(godIntent)
    }

    @JvmStatic
    fun clear() {
        GodIntentObservable.getInstance().clear()
    }
}
