package com.ddu.ui.adapter;

import android.content.Context;

import com.ddu.icore.ui.adapter.common.DefaultRecycleViewAdapter;
import com.ddu.icore.ui.adapter.common.ViewHolder;

import java.util.List;

/**
 * Created by yzbzz on 2018/3/5.
 */

public class RVAdapterJ extends DefaultRecycleViewAdapter {

    public RVAdapterJ(Context context, List items) {
        super(context, items);
    }

    @Override
    public int getLayoutId(int viewType) {
        return 0;
    }

    @Override
    public void bindView(ViewHolder viewHolder, Object data, int position) {
//      AlertDialogFragment.Builder builder = new AlertDialogFragment.Builder();
//      builder.setTitle("");
//      builder.setMessage("");
//      builder.setLeftText("", new AlertDialogFragment.IButtonClickListener() {
//          @Override
//          public void onClick(View v, ShowDialogFragment dialogFragment) {
//
//          }
//      });
//      builder.setRightText("", new AlertDialogFragment.IButtonClickListener() {
//          @Override
//          public void onClick(View v, ShowDialogFragment dialogFragment) {
//
//          }
//      });
        
    }
}
