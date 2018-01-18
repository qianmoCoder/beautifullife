package com.ddu.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ddu.R;
import com.ddu.icore.ui.view.NumberInputView;
import com.ddu.icore.util.AnimatorUtils;
import com.ddu.icore.util.sys.ViewUtils;

import static com.ddu.R.id.tv_error_msg;

/**
 * Created by yzbzz on 2017/5/26.
 */

public class QRCodeDialog extends DialogFragment implements View.OnClickListener {

    private TextView mTvRefresh;
    private TextView mTvErrorMsg;
    private ImageView mIvQrCode;
    private NumberInputView mEtPhoneNumber;

    private Context mContext;

    public static QRCodeDialog newInstance(String phone) {
        Bundle args = new Bundle();
        args.putString(VerificationCodeDialog.Companion.getEXTRA_PHONE(), phone);
        QRCodeDialog fragment = new QRCodeDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        final LinearLayout mLlLogin = (LinearLayout) inflater.inflate(R.layout.fragment_qr_code, container, false);

        mTvErrorMsg = ViewUtils.findViewById(mLlLogin, tv_error_msg);
        mEtPhoneNumber = ViewUtils.findViewById(mLlLogin, R.id.et_phone_number);
        mEtPhoneNumber.setOnInputCallback(new NumberInputView.OnInputCallback() {
            @Override
            public void onInputComplete(String inputText) {
                mTvErrorMsg.setVisibility(View.VISIBLE);
                mLlLogin.startAnimation(AnimatorUtils.shake(mContext));
                startTo();
                dismissAllowingStateLoss();
            }
        });
        mIvQrCode = ViewUtils.findViewById(mLlLogin, R.id.iv_qr_code);
        mTvRefresh = ViewUtils.findViewById(mLlLogin, R.id.tv_refresh);
        mIvQrCode.setOnClickListener(this);
        mTvRefresh.setOnClickListener(this);
        return mLlLogin;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_qr_code:
            case R.id.tv_refresh:
                refreshQrCode();
                break;
        }
    }

    private void refreshQrCode() {

    }

    private void startTo() {
        String tempPhone = getArguments().getString(VerificationCodeDialog.Companion.getEXTRA_PHONE(), "");
        VerificationCodeDialog verificationCodeDialog = VerificationCodeDialog.Companion.newInstance(tempPhone);
        verificationCodeDialog.show(getFragmentManager(), "verificationCodeDialog");
    }

}
