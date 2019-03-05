package com.ddu.icore.help;

import android.content.Context;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

/**
 * Created by yzbzz on 2018/12/19.
 */
public class ClickAtUserSpan  extends ClickableSpan {

    private View.OnClickListener onClickListener;

    public ClickAtUserSpan(Context context, int color, View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onClick(View view) {
        if (onClickListener != null) {
            ((TextView) view).setMovementMethod(CustomLinkMovementMethod.getInstance());
            onClickListener.onClick(view);
        }
    }

}
