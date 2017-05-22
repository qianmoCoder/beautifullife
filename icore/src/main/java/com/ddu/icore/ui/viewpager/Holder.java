package com.ddu.icore.ui.viewpager;

import android.content.Context;
import android.view.View;

/**
 * Created by yzbzz on 2017/5/22.
 */

public interface Holder<T> {
    View createView(Context context);
    void UpdateUI(Context context,int position,T data);
}
