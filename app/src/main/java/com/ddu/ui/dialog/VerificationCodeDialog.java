package com.ddu.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ddu.R;
import com.ddu.icore.ui.view.NumberInputView;
import com.ddu.icore.util.AnimatorUtils;
import com.ddu.icore.util.sys.ViewUtils;

import static com.ddu.R.id.tv_error_msg;
import static com.ddu.R.id.tv_phone_number;

/**
 * Created by yzbzz on 2017/5/26.
 */

public class VerificationCodeDialog extends DialogFragment implements View.OnClickListener {

    public static final String EXTRA_PHONE = "extra_phone";

    private static final int COUNT_DOWN_TIME = 60 * 1000;
    private static final long COUNT_DOWN_INTERVAL = 1000;

    private TextView mTvPhoneNumber;
    private TextView mTvCountDown;
    private TextView mTvResend;
    private TextView mTvErrorMsg;
    private LinearLayout mLlVoiceCode;
    private NumberInputView mEtPhoneNumber;

    private RegisterTimer mRegisterTimer;

    private String mPhone = "";

    private Context mContext;

    public static VerificationCodeDialog newInstance(String phone) {
        Bundle args = new Bundle();
        args.putString(EXTRA_PHONE, phone);
        VerificationCodeDialog fragment = new VerificationCodeDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPhone = getArguments().getString(EXTRA_PHONE);
        mRegisterTimer = new RegisterTimer(COUNT_DOWN_TIME, 250);
        mRegisterTimer.start();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final LinearLayout mLlLogin = (LinearLayout) inflater.inflate(R.layout.fragment_verification_code, container, false);

        mTvErrorMsg = ViewUtils.findViewById(mLlLogin, tv_error_msg);
        mTvPhoneNumber = ViewUtils.findViewById(mLlLogin, tv_phone_number);
        mTvPhoneNumber.setText(mPhone);

        mLlVoiceCode = ViewUtils.findViewById(mLlLogin, R.id.ll_voice_code);
        mEtPhoneNumber = ViewUtils.findViewById(mLlLogin, R.id.et_phone_number);
        mEtPhoneNumber.setOnInputCallback(new NumberInputView.OnInputCallback() {
            @Override
            public void onInputComplete(String inputText) {
                mTvErrorMsg.setVisibility(View.VISIBLE);
                mLlLogin.startAnimation(AnimatorUtils.shake(mContext));
            }
        });
        mTvCountDown = ViewUtils.findViewById(mLlLogin, R.id.tv_count_down);
        mTvResend = ViewUtils.findViewById(mLlLogin, R.id.btn_resend);
        mTvResend.setOnClickListener(this);
        return mLlLogin;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_resend:
                reSend();
                break;
        }
    }

    private void reSend() {
        mTvResend.setVisibility(View.GONE);
        mTvCountDown.setVisibility(View.VISIBLE);
        mRegisterTimer.start();
    }

    class RegisterTimer extends CountDownTimer {

        public RegisterTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            mTvResend.setVisibility(View.VISIBLE);
            mTvCountDown.setVisibility(View.GONE);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (isAdded()) {
                mTvCountDown.setText(getResources().getString(R.string.format_count_down, millisUntilFinished / 1000));
                if (millisUntilFinished / 1000 == COUNT_DOWN_TIME / 1000 - 20) {
                    mLlVoiceCode.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRegisterTimer.cancel();
    }
}
