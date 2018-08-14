package com.ddu.icore.dialog;

import android.support.v4.app.DialogFragment;

import com.ddu.icore.callback.Consumer3;
import com.ddu.icore.entity.BottomItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzbzz on 2018/8/8.
 */
public class BottomDialogParams {

    public static final int TYPE_LINEAR = 0x01;
    public static final int TYPE_GRID = 0x02;

    public CharSequence mTitle;
    public CharSequence mBottomText;

    public List<BottomItem> mItems = new ArrayList<>();

    public int mIconWidth;
    public int mIconHeight;

    public int mItemHeight;

    public int mScale = 3;

    public int mLayoutType = TYPE_LINEAR;

    public int mSpanCount = 4;

    public Consumer3<DialogFragment, BottomItem, Integer> mConsumer3;

}
