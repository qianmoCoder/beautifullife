package com.ddu.icore.util

import android.app.Activity
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow

import com.ddu.icore.R


/**
 * Created by yzbzz on 16/4/18.
 */
object PopupUtils {

    fun showTopRightPopupWindow(activity: Activity, view: View, popupView: View) {
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)

        val location = IntArray(2)
        view.getLocationOnScreen(location)

        val popupWindow = showPopupWindow(popupView)
        popupWindow.showAtLocation(view, Gravity.TOP or Gravity.RIGHT, 0, location[1] + frame.top - popupWindow.height)
        popupWindow.update()

    }

    fun showPopupWindow(popupView: View): PopupWindow {
        val popupWindow = PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        popupWindow.isFocusable = true
        popupWindow.isOutsideTouchable = true
        popupWindow.setBackgroundDrawable(BitmapDrawable())
        popupWindow.animationStyle = R.style.MorePopupAnim
        return popupWindow
    }
}
