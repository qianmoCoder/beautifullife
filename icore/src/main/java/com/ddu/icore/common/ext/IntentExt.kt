package com.ddu.icore.common.ext

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

inline fun <reified T : Activity> Context.startActivity(vararg args: Pair<String, Any?>) {
    ICoreInternals.internalStartActivity(this, T::class.java, *args)
}

inline fun <reified T : Activity> Fragment.startActivity(vararg args: Pair<String, Any?>) {
    activity?.apply {
        ICoreInternals.internalStartActivity(this, T::class.java, *args)
    }
}

inline fun <reified T : Activity> Activity.startActivityForResult(requestCode: Int, vararg args: Pair<String, Any?>) {
    ICoreInternals.internalStartActivityForResult(this, T::class.java, requestCode, *args)
}

inline fun <reified T : Activity> Fragment.startActivityForResult(requestCode: Int, vararg args: Pair<String, Any?>) {
    activity?.apply {
        startActivityForResult(ICoreInternals.createIntent(this, T::class.java, *args), requestCode)
    }
}

inline fun <reified T : Service> Context.stopService(vararg args: Pair<String, Any?>) =
        ICoreInternals.internalStopService(this, T::class.java, *args)

inline fun <reified T : Service> Context.startService(vararg args: Pair<String, Any?>) =
        ICoreInternals.internalStartService(this, T::class.java, *args)

inline fun <reified T : Any> Context.intentFor(vararg args: Pair<String, Any?>): Intent =
        ICoreInternals.createIntent(this, T::class.java, *args)

inline fun <reified T : Any> Fragment.intentFor(vararg args: Pair<String, Any?>): Intent? =
        activity?.let {
            ICoreInternals.createIntent(it, T::class.java, *args)
        }


