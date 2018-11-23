package com.ddu.ui.dialog

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import com.ddu.R
import com.ddu.R.id.tv_error_msg
import com.ddu.R.id.tv_phone_number
import com.ddu.icore.common.ctx
import com.ddu.icore.common.loadAnimation
import com.ddu.icore.common.showKeyboard
import com.ddu.icore.ui.view.NumberInputView
import com.ddu.icore.util.sys.ViewUtils

/**
 * Created by yzbzz on 2017/5/26.
 */

class VerificationCodeDialog : DialogFragment(), View.OnClickListener {

    private var mTvPhoneNumber: TextView? = null
    private var mTvCountDown: TextView? = null
    private var mTvResend: TextView? = null
    private var mTvErrorMsg: TextView? = null
    private var mLlVoiceCode: LinearLayout? = null
    private var mEtPhoneNumber: NumberInputView? = null

    private var mRegisterTimer: RegisterTimer? = null

    private var mPhone: String? = ""

    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPhone = arguments!!.getString(EXTRA_PHONE)
        mRegisterTimer = RegisterTimer(COUNT_DOWN_TIME.toLong(), 250)
        mRegisterTimer!!.start()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

        val mLlLogin = inflater.inflate(R.layout.fragment_verification_code, container, false) as LinearLayout

        mTvErrorMsg = ViewUtils.findViewById(mLlLogin, tv_error_msg)
        mTvPhoneNumber = ViewUtils.findViewById(mLlLogin, tv_phone_number)
        mTvPhoneNumber!!.text = mPhone

        mLlVoiceCode = ViewUtils.findViewById(mLlLogin, R.id.ll_voice_code)
        mEtPhoneNumber = ViewUtils.findViewById(mLlLogin, R.id.et_phone_number)
        mEtPhoneNumber!!.postDelayed({
            //                mEtPhoneNumber.getEt().performClick();
            //                mEtPhoneNumber.getll().performClick();
            activity?.showKeyboard(mEtPhoneNumber)
        }, 300)
        mEtPhoneNumber!!.setOnInputCallback {
            mTvErrorMsg!!.visibility = View.VISIBLE
            mLlLogin.startAnimation(ctx.loadAnimation(R.anim.shake))
        }
        mTvCountDown = ViewUtils.findViewById(mLlLogin, R.id.tv_count_down)
        mTvResend = ViewUtils.findViewById(mLlLogin, R.id.btn_resend)
        mTvResend!!.setOnClickListener(this)
        return mLlLogin
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.btn_resend -> {
                mEtPhoneNumber!!.clearText()
                reSend()
            }
        }
    }

    private fun reSend() {
        mTvResend!!.visibility = View.GONE
        mTvCountDown!!.visibility = View.VISIBLE
        mRegisterTimer!!.start()
    }

    internal inner class RegisterTimer(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

        override fun onFinish() {
            mTvResend!!.visibility = View.VISIBLE
            mTvCountDown!!.visibility = View.GONE
        }

        override fun onTick(millisUntilFinished: Long) {
            if (isAdded) {
                mTvCountDown!!.text = resources.getString(R.string.format_count_down, millisUntilFinished / 1000)
                if (millisUntilFinished / 1000 == (COUNT_DOWN_TIME / 1000 - 20).toLong()) {
                    mLlVoiceCode!!.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mRegisterTimer!!.cancel()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            mEtPhoneNumber!!.performClick()
        }
    }

    companion object {

        val EXTRA_PHONE = "extra_phone"

        private val COUNT_DOWN_TIME = 60 * 1000
        private val COUNT_DOWN_INTERVAL: Long = 1000

        fun newInstance(phone: String): VerificationCodeDialog {
            val args = Bundle()
            args.putString(EXTRA_PHONE, phone)
            val fragment = VerificationCodeDialog()
            fragment.arguments = args
            return fragment
        }
    }
}
