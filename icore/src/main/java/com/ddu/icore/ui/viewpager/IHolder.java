package com.ddu.icore.ui.viewpager;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * Created by yzbzz on 2017/3/9.
 */

public interface IHolder<T> extends Holder {

    View createView(Context context, List<T> mData, int position);
}


