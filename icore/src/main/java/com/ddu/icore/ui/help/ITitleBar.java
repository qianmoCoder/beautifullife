package com.ddu.icore.ui.help;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by yzbzz on 2017/1/23.
 */

public interface ITitleBar {

    @NonNull
    View getView();

    LinearLayout getTitleBarLeft();

    LinearLayout getTitleBarMiddle();

    LinearLayout getTitleBarRight();

    ImageView getLeftImg();

    TextView getLeftText();

    TextView getMiddleText();

    TextView getRightText();
}
