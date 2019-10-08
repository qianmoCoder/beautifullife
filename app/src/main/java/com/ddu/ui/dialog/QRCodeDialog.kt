package com.ddu.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.ddu.R
import com.ddu.icore.common.ext.ctx
import com.ddu.icore.common.ext.loadAnimation
import kotlinx.android.synthetic.main.fragment_qr_code.*

/**
 * Created by yzbzz on 2017/5/26.
 */

class QRCodeDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val mLlLogin = inflater.inflate(R.layout.fragment_qr_code, container, false) as LinearLayout

        et_phone_number.setOnInputCallback {
            tv_error_msg.visibility = View.VISIBLE
            mLlLogin.startAnimation(ctx.loadAnimation(com.ddu.icore.R.anim.shake))
            startTo()
            dismissAllowingStateLoss()
        }
        return mLlLogin
    }


    private fun startTo() {
        val tempPhone = arguments?.getString(VerificationCodeDialog.EXTRA_PHONE, "") ?: ""
        val verificationCodeDialog = VerificationCodeDialog.newInstance(tempPhone)
        verificationCodeDialog.show(fragmentManager, "verificationCodeDialog")
    }

    companion object {

        fun newInstance(phone: String): QRCodeDialog {
            val args = Bundle()
            args.putString(VerificationCodeDialog.EXTRA_PHONE, phone)
            val fragment = QRCodeDialog()
            fragment.arguments = args
            return fragment
        }
    }

}
