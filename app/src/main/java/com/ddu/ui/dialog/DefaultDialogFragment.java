package com.ddu.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.Space;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ddu.R;
import com.ddu.icore.util.ViewUtils;


/**
 * Created by lhz on 16/3/3.
 */
public class DefaultDialogFragment extends DialogFragment implements View.OnClickListener {

    private static final String EXTRA_IMG_RES = "EXTRA_IMG_RES";
    private static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private static final String EXTRA_TITLE_LEFT = "EXTRA_TITLE_LEFT";
    private static final String EXTRA_TITLE_RIGHT = "EXTRA_TITLE_RIGHT";

    private Context mContext;

    @Nullable
    private String message;
    private int imgRes;

    private String btnOKTitle;
    private String btnCancelTitle;

    private IButtonListener mButtonListener;

    @NonNull
    public static DefaultDialogFragment newInstance(int imgRes, String message, String leftTitle, String rightTitle, IButtonListener buttonListener) {
        DefaultDialogFragment dialog = new DefaultDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_IMG_RES, imgRes);
        bundle.putString(EXTRA_MESSAGE, message);
        bundle.putString(EXTRA_TITLE_LEFT, leftTitle);
        bundle.putString(EXTRA_TITLE_RIGHT, rightTitle);
        dialog.setArguments(bundle);
        dialog.setButtonListener(buttonListener);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        message = getArguments().getString(EXTRA_MESSAGE);
        imgRes = getArguments().getInt(EXTRA_IMG_RES);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.dialog_default, container, false);

        TextView textView = ViewUtils.findViewById(linearLayout, R.id.tv_message);
        textView.setText(message);

        ImageView imageView = ViewUtils.findViewById(linearLayout, R.id.iv_icon);
        imageView.setImageResource(imgRes);

        Button btnCancel = ViewUtils.findViewById(linearLayout, R.id.btn_left);
        Button btnOK = ViewUtils.findViewById(linearLayout, R.id.btn_right);

        Space space = ViewUtils.findViewById(linearLayout, R.id.space);

        if (!(mButtonListener instanceof ITwoButtonListener)) {
            btnCancel.setVisibility(View.GONE);
            space.setVisibility(View.GONE);
        }

        btnCancel.setText(btnCancelTitle);
        btnOK.setText(btnOKTitle);

        btnCancel.setOnClickListener(this);
        btnOK.setOnClickListener(this);

        return linearLayout;
    }


    @Override
    public void onClick(@NonNull View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_left:
                if (mButtonListener instanceof ITwoButtonListener) {
                    ((ITwoButtonListener) mButtonListener).onBtnCancelClickListener(this);
                }
                break;
            case R.id.btn_right:
                if (null != mButtonListener) {
                    mButtonListener.onBtnClickListener(this);
                }
                break;
            default:
                break;
        }
    }

    public void setButtonListener(IButtonListener buttonListener) {
        mButtonListener = buttonListener;
    }

    public interface IButtonListener {
        void onBtnClickListener(DialogFragment dialog);
    }

    public interface ITwoButtonListener extends IButtonListener {

        void onBtnCancelClickListener(DialogFragment dialog);
    }
}
