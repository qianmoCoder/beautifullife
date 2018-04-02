package com.ddu.icore.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.WindowManager
import com.ddu.icore.R
import org.jetbrains.anko.support.v4.act


open class BottomDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(act, theme)

        val dialog = builder.create()
        dialog.show()

        val window = dialog.window
        window.setBackgroundDrawableResource(android.R.color.transparent)

        val wlp = window.attributes
        wlp.gravity = Gravity.BOTTOM
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT
        wlp.windowAnimations = R.style.BottomDialogAnim
        window.attributes = wlp

        return dialog
    }

}
