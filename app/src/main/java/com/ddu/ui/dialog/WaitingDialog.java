package com.ddu.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ddu.R;

import androidx.annotation.Nullable;


/**
 * Created by yzbzz on 2017/10/31.
 */

public class WaitingDialog extends androidx.fragment.app.DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_waiting, container, false);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAllowingStateLoss();
            }
        });
        return linearLayout;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.v("lhz","onCancel");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v("lhz","onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v("lhz","onDetach");
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.v("lhz","onDismiss");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("lhz","onDestroy");
    }
}
