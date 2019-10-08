package com.ddu.icore.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.*
import com.ddu.icore.R
import com.ddu.icore.common.ext.act


abstract class AbsBottomDialogFragment : androidx.fragment.app.DialogFragment() {

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(act, theme)

        val dialog = builder.create()
        dialog.show()

        val window = dialog.window
        window?.setBackgroundDrawableResource(android.R.color.transparent)

        val wlp = window?.attributes
        wlp?.run {
            gravity = Gravity.BOTTOM
            width = WindowManager.LayoutParams.MATCH_PARENT
            windowAnimations = R.style.BottomDialogAnim
        }
        window?.attributes = wlp

        return dialog
    }

    abstract fun getLayoutId(): Int

}
