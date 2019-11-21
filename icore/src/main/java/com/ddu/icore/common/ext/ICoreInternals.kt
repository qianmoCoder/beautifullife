package com.ddu.icore.common.ext

import android.app.Activity
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

object ICoreInternals {

    @JvmStatic
    fun <T> createIntent(ctx: Context, clazz: Class<out T>, vararg args: Pair<String, Any?>): Intent {
        val intent = Intent(ctx, clazz)
        if (args.isNotEmpty()) {
            fillIntentArguments(intent, *args)
        }
        return intent
    }

    @JvmStatic
    fun internalStartActivity(
            ctx: Context,
            activity: Class<out Activity>,
            vararg args: Pair<String, Any?>
    ) {
        ctx.startActivity(createIntent(ctx, activity, *args))
    }

    @JvmStatic
    fun internalStartActivityForResult(
            act: Activity,
            activity: Class<out Activity>,
            requestCode: Int,
            vararg args: Pair<String, Any?>
    ) {
        act.startActivityForResult(createIntent(act, activity, *args), requestCode)
    }

    @JvmStatic
    fun internalStartService(
            ctx: Context,
            service: Class<out Service>,
            vararg args: Pair<String, Any?>
    ): ComponentName? = ctx.startService(createIntent(ctx, service, *args))

    @JvmStatic
    fun internalStopService(
            ctx: Context,
            service: Class<out Service>,
            vararg args: Pair<String, Any?>
    ): Boolean = ctx.stopService(createIntent(ctx, service, *args))


    @JvmStatic
    fun fillIntentArguments(intent: Intent, vararg pairs: Pair<String, Any?>) {
        intent.apply {
            for ((key, value) in pairs) {
                when (value) {
                    null -> putExtra(key, null as Serializable?) // Any nullable type will suffice.

                    // Scalars
                    is Boolean -> putExtra(key, value)
                    is Byte -> putExtra(key, value)
                    is Char -> putExtra(key, value)
                    is Double -> putExtra(key, value)
                    is Float -> putExtra(key, value)
                    is Int -> putExtra(key, value)
                    is Long -> putExtra(key, value)
                    is Short -> putExtra(key, value)

                    // References
                    is String -> putExtra(key, value)
                    is Bundle -> putExtra(key, value)
                    is CharSequence -> putExtra(key, value)
                    is Parcelable -> putExtra(key, value)
                    // Last resort. Also we must check this after Array<*> as all arrays are serializable.
                    is Serializable -> putExtra(key, value)

                    // Scalar arrays
                    is BooleanArray -> putExtra(key, value)
                    is ByteArray -> putExtra(key, value)
                    is CharArray -> putExtra(key, value)
                    is DoubleArray -> putExtra(key, value)
                    is FloatArray -> putExtra(key, value)
                    is IntArray -> putExtra(key, value)
                    is LongArray -> putExtra(key, value)
                    is ShortArray -> putExtra(key, value)

                    // Reference arrays
                    is Array<*> -> {
                        val componentType = value::class.java.componentType!!
                        @Suppress("UNCHECKED_CAST") // Checked by reflection.
                        when {
                            Parcelable::class.java.isAssignableFrom(componentType) -> {
                                putExtra(key, value as Array<Parcelable>)
                            }
                            String::class.java.isAssignableFrom(componentType) -> {
                                putExtra(key, value as Array<String>)
                            }
                            CharSequence::class.java.isAssignableFrom(componentType) -> {
                                putExtra(key, value as Array<CharSequence>)
                            }
                            Int::class.java.isAssignableFrom(componentType) -> {
                                putExtra(key, value as Array<Int>)
                            }

                            else -> {
                                val valueType = componentType.canonicalName
                                throw IllegalArgumentException(
                                        "Illegal value array type $valueType for key \"$key\"")
                            }
                        }
                    }
                }
            }
        }
    }
}



