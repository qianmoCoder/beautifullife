package com.ddu.ui.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ddu.R;
import com.ddu.icore.util.sys.ViewUtils;

/**
 * Created by yzbzz on 2017/5/26.
 */

public class LoginDialog extends DialogFragment implements View.OnClickListener {

    private final static int PHONE_NUMBER_LENGTH = 11;
    private final static int SPACE_NUMBER = 0;
    private static int EDIT_LENGTH = PHONE_NUMBER_LENGTH + SPACE_NUMBER;

    private EditText mEtPhoneNumber;
    private TextView mTvToast;
    private Button mBtnNext;
    private String mPhoneNum;

    private boolean isFirstClick = true;

    private String tagItem = "default";

    public static LoginDialog newInstance() {

        Bundle args = new Bundle();

        LoginDialog fragment = new LoginDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Log.v("lhz", "tag: " + tagItem);
        LinearLayout mLlLogin = (LinearLayout) inflater.inflate(R.layout.fragment_login, container, false);
        mTvToast = ViewUtils.findViewById(mLlLogin, R.id.tv_toast);
        mBtnNext = ViewUtils.findViewById(mLlLogin, R.id.btn_next);
        mBtnNext.setOnClickListener(this);
        mEtPhoneNumber = ViewUtils.findViewById(mLlLogin, R.id.et_phone_number);
        mEtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null || s.length() == 0) return;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    sb.append(s.charAt(i));
                }
                try {
                    if (!sb.toString().equals(s.toString())) {
                        int index = start + 1;
                        if (sb.charAt(start) == ' ') {
                            if (before == 0) {
                                index++;
                            } else {
                                index--;
                            }
                        } else {
                            if (before == 1) {
                                index--;
                            }
                        }
                        mEtPhoneNumber.setText(sb.toString());
                        mEtPhoneNumber.setSelection(index);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                mPhoneNum = mEtPhoneNumber.getText().toString();
                mPhoneNum = mPhoneNum.replaceAll(" ", "");
                updateStatus();
            }
        });
        return mLlLogin;
    }

    private void updateStatus() {
        if (mPhoneNum.length() >= EDIT_LENGTH) {
            if (mPhoneNum.startsWith("1")) {
                mTvToast.setVisibility(View.INVISIBLE);
                mBtnNext.setEnabled(true);
            } else {
                mTvToast.setVisibility(View.VISIBLE);
            }
        } else {
            mBtnNext.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_next:
                doNext();
                break;
        }
    }

    private void doNext() {
        if (isFirstClick) {
            isFirstClick = false;
            VerificationCodeDialog verificationCodeDialog = VerificationCodeDialog.Companion.newInstance(mPhoneNum);
            verificationCodeDialog.show(getFragmentManager(), "verificationCodeDialog");
        } else {
            QRCodeDialog qrCodeDialog = QRCodeDialog.Companion.newInstance(mPhoneNum);
            qrCodeDialog.show(getFragmentManager(), "qrCodeDialog");
        }
    }

    public void showDialog() {
        show(getFragmentManager(), "LoginDialog");
    }

    public void showDialog(FragmentActivity fragmentActivity) {
        show(fragmentActivity.getSupportFragmentManager(), "LoginDialog");
    }

    public void setTag(String tag) {
        this.tagItem = tag;
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        return super.show(transaction, tag);
    }
}
