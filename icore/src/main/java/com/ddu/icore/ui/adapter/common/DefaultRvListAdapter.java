package com.ddu.icore.ui.adapter.common;

import android.content.Context;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

/**
 * Created by yzbzz on 2019-08-05.
 */
public abstract class DefaultRvListAdapter<T> extends AbsRVListAdapter<T> {

    private Context mContext;
    protected List<T> mItems;

    public DefaultRvListAdapter(Context context, List<T> items, DiffUtil.ItemCallback<T> itemCallback) {
        super(itemCallback);
        mContext = context;
        mItems = items;
    }

    @Override
    public DefaultRVAdapter<T> getWrappedAdapter() {
        return new DefaultRVAdapter<T>(mContext, mItems) {
            @Override
            public int getLayoutId(int viewType) {
                return getRvLayoutId(viewType);
            }

            @Override
            public void bindView(ViewHolder viewHolder, T data, int position) {
                bindRvView(viewHolder, data, position);
            }
        };
    }

    public abstract int getRvLayoutId(int viewType);

    public abstract void bindRvView(ViewHolder viewHolder, T data, int position);

}
