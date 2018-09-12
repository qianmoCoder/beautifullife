package com.ddu.ui.fragment.study.ui;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ProgressBar;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.iannotation.IElement;

/**
 * Created by yzbzz on 2017/5/3.
 */
@IElement("UI")
public class DialogFragment extends DefaultFragment {

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_study_ui_dialog;
    }

    @Override
    public void initView() {
        int[][] mStates = new int[4][];
        mStates[0] = new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed};
        mStates[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        mStates[3] = new int[]{-android.R.attr.state_enabled};
        mStates[2] = new int[]{android.R.attr.state_enabled};
        int[] colors = new int[]{Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE};

        ProgressBar pb = findViewById(R.id.pb_c);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable wrapDrawable = DrawableCompat.wrap(pb.getIndeterminateDrawable());
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(getContext(), R.color.c_4897fa));
            pb.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
        } else {
            pb.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.c_4897fa), PorterDuff.Mode.SRC_IN);
        }


    }
}
