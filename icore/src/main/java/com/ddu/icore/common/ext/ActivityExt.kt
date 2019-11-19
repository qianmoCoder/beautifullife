package com.ddu.icore.common.ext

import android.app.Activity
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

var Activity.screenBrightness
    get() = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS)
    set(value) {
        val lp = window.attributes
        lp.screenBrightness = value / 255f
        window.attributes = lp
    }

fun Activity.hideKeyboard(view: View?): Boolean? {
    val currentView = currentFocus ?: view
    currentView?.let {
        return inputMethodManager.hideSoftInputFromWindow(
                currentView.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
    return false
}

fun Activity.showKeyboard(view: View?): Boolean? {
    val currentView = currentFocus ?: view
    currentView?.let {
        return inputMethodManager.showSoftInput(currentView, InputMethodManager.HIDE_NOT_ALWAYS)
    }
    return false
}

inline fun <reified F : Fragment> FragmentActivity.newFragment(vararg args: Pair<String, Any?>): F {
    val fragment = supportFragmentManager.fragmentFactory.instantiate(
            ClassLoader.getSystemClassLoader(),
            F::class.java.name
    )
    fragment.arguments = bundleOf(*args)
    return fragment as F
}
