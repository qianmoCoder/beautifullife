
package com.ddu.icore.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ddu.icore.R;
import com.ddu.icore.util.ViewUtils;

public class ShareDialogFragment extends BottomDialogFragment implements View.OnClickListener {

    private TextView mTvCancel;

    public static ShareDialogFragment newInstance() {
        ShareDialogFragment dialog = new ShareDialogFragment();
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_share, container, false);

        mTvCancel = ViewUtils.findViewById(linearLayout, R.id.tv_cancel);
        mTvCancel.setOnClickListener(this);

        return linearLayout;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_cancel) {
            dismissAllowingStateLoss();
        }
    }
}
