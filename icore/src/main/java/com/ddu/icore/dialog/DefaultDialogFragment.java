
package com.ddu.icore.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ddu.icore.R;
import com.ddu.icore.util.sys.ViewUtils;


public class DefaultDialogFragment extends DialogFragment implements View.OnClickListener {

    private static final String EXTRA_TITLE = "EXTRA_TITLE";
    private static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private static final String EXTRA_LEFT_TEXT = "EXTRA_LEFT_TEXT";
    private static final String EXTRA_RIGHT_TEXT = "EXTRA_RIGHT_TEXT";

    private static final String EXTRA_MSG_GRAVITY = "EXTRA_MSG_GRAVITY";
    private static final String EXTRA_MSG_SIZE = "EXTRA_MSG_SIZE";

    private static final String EXTRA_LEFT_COLOR = "EXTRA_LEFT_COLOR";
    private static final String EXTRA_RIGHT_COLOR = "EXTRA_RIGHT_COLOR";

    private String title = "";
    private String msg = "";
    private String leftText = "";
    private String rightText = "";

    private TextView mTvTitle;
    private TextView mTvMsg;
    private TextView mTvBtnLeft;
    private TextView mTvBtnRight;

    private int msgGravity = Gravity.CENTER;

    private int leftColor = R.color.c_4897fa;
    private int rightColor = R.color.c_272727;

    private View mViewLine;

    private DialogFragment mDialogFragment;

    private IButtonClickListener mLeftClickListener;
    private IButtonClickListener mRightClickListener;

    private Bundle bundle;

    private int size = 17;

    public static DefaultDialogFragment newInstance() {
        DefaultDialogFragment dialog = new DefaultDialogFragment();
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialogFragment = this;

        bundle = getArguments();

        if (null != bundle) {
            title = bundle.getString(EXTRA_TITLE, "");
            msg = bundle.getString(EXTRA_MESSAGE, "");
            leftText = bundle.getString(EXTRA_LEFT_TEXT, "");
            rightText = bundle.getString(EXTRA_RIGHT_TEXT, "");

            msgGravity = bundle.getInt(EXTRA_MSG_GRAVITY, Gravity.CENTER);
            size = bundle.getInt(EXTRA_MSG_SIZE, 17);

            leftColor = bundle.getInt(EXTRA_LEFT_COLOR, R.color.c_4897fa);
            rightColor = bundle.getInt(EXTRA_RIGHT_COLOR, R.color.c_272727);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_dialog_default, container, false);

        mTvTitle = ViewUtils.findViewById(linearLayout, R.id.tv_dialog_title);
        mViewLine = ViewUtils.findViewById(linearLayout, R.id.view_line);
        mTvMsg = ViewUtils.findViewById(linearLayout, R.id.tv_dialog_msg);
        mTvBtnLeft = ViewUtils.findViewById(linearLayout, R.id.tv_dialog_btn_left);
        mTvBtnRight = ViewUtils.findViewById(linearLayout, R.id.tv_dialog_btn_right);

        if (!TextUtils.isEmpty(title)) {
            mTvTitle.setText(title);
            mTvTitle.setVisibility(View.VISIBLE);
            mViewLine.setVisibility(View.VISIBLE);
        }

        mTvMsg.setText(msg);
        mTvMsg.setGravity(msgGravity);
        mTvMsg.setTextSize(17);
        mTvBtnLeft.setText(leftText);
        int color = getResources().getColor(leftColor);
        mTvBtnLeft.setTextColor(color);
        if (!TextUtils.isEmpty(rightText)) {
            color = getResources().getColor(rightColor);
            mTvBtnRight.setVisibility(View.VISIBLE);
            mTvBtnRight.setText(rightText);
            mTvBtnRight.setTextColor(color);
        }

        mTvBtnLeft.setOnClickListener(this);
        mTvBtnRight.setOnClickListener(this);

        return linearLayout;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_dialog_btn_left && null != mLeftClickListener) {
            mLeftClickListener.onClick(v, this);
        } else if (id == R.id.tv_dialog_btn_right && null != mRightClickListener) {
            mRightClickListener.onClick(v, this);
        }
    }

    public static class Builder {

        private String mTitle;
        private String mMessage;
        private String mLeftText;
        private String mRightText;
        private IButtonClickListener mLeftButtonClickListener;
        private IButtonClickListener mRightButtonClickListener;

        private int mGravity = Gravity.CENTER;
        private int mMsgSize;

        private int mLeftColor = R.color.c_4897fa;
        private int mRightColor = R.color.c_272727;

        public Builder() {

        }

        public Builder setTitle(String title) {
            if (null == title) {
                title = "";
            }
            mTitle = title;
            return this;
        }

        public Builder setMessage(String message) {
            if (null == message) {
                message = "";
            }
            mMessage = message;
            return this;
        }

        public Builder setMessageGravity(int gravity) {
            mGravity = gravity;
            return this;
        }

        public Builder setMessageSize(int size) {
            mMsgSize = size;
            return this;
        }

        public Builder setLeftColor(int color) {
            mLeftColor = color;
            return this;
        }

        public Builder setRightColor(int color) {
            mRightColor = color;
            return this;
        }

        public Builder setLeftText(String leftText, IButtonClickListener iButtonClickListener) {
            if (null == leftText) {
                leftText = "";
            }
            mLeftText = leftText;
            mLeftButtonClickListener = iButtonClickListener;
            return this;
        }

        public Builder setRightText(String rightText, IButtonClickListener iButtonClickListener) {
            if (null == rightText) {
                rightText = "";
            }
            mRightText = rightText;
            mRightButtonClickListener = iButtonClickListener;
            return this;
        }

        public DefaultDialogFragment create() {
            DefaultDialogFragment dialog = new DefaultDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_TITLE, mTitle);
            bundle.putString(EXTRA_MESSAGE, mMessage);
            bundle.putString(EXTRA_LEFT_TEXT, mLeftText);
            bundle.putString(EXTRA_RIGHT_TEXT, mRightText);
            bundle.putInt(EXTRA_MSG_GRAVITY, mGravity);
            bundle.putInt(EXTRA_LEFT_COLOR, mLeftColor);
            bundle.putInt(EXTRA_RIGHT_COLOR, mRightColor);
            dialog.setArguments(bundle);
            dialog.setLeftOnClickListener(mLeftButtonClickListener);
            dialog.setRightOnClickListener(mRightButtonClickListener);
            return dialog;
        }

        public DefaultDialogFragment show(FragmentManager fragmentManager, String tag) {
            DefaultDialogFragment dialog = create();
            dialog.show(fragmentManager, tag);
            return dialog;
        }
    }

    public void setLeftOnClickListener(IButtonClickListener iButtonClickListener) {
        mLeftClickListener = iButtonClickListener;
    }

    public void setRightOnClickListener(IButtonClickListener iButtonClickListener) {
        mRightClickListener = iButtonClickListener;
    }

    public interface IButtonClickListener {
        void onClick(View v, DialogFragment dialogFragment);
    }

}
