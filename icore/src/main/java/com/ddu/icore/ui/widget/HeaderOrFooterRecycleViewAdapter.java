package com.ddu.icore.ui.widget;

import android.view.View;
import android.view.ViewGroup;

import com.ddu.icore.ui.adapter.common.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class HeaderOrFooterRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements WrapperRecyclerViewAdapter {

    public static final int ITEM_VIEW_TYPE_HEADER = -1;
    public static final int ITEM_VIEW_TYPE_FOOTER = -2;

    private ArrayList<FixedViewInfo> mHeaderViewInfos = new ArrayList<>();
    private ArrayList<FixedViewInfo> mFooterViewInfos = new ArrayList<>();

    private RecyclerView.Adapter mAdapter;

    public HeaderOrFooterRecycleViewAdapter(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
        setHasStableIds(mAdapter.hasStableIds());
    }

    @Override
    public int getItemViewType(int position) {
        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            return ViewTypeSpec.makeItemViewTypeSpec(position, ViewTypeSpec.HEADER);
        }

        int adjPosition = position - numHeaders;
        int adapterCount = 0;

        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemViewType(adjPosition);
            }
        }

        return ViewTypeSpec.makeItemViewTypeSpec(adjPosition - adapterCount, ViewTypeSpec.FOOTER);
    }

    @Override
    public int getItemCount() {
        if (null != mAdapter) {
            return getHeadersCount() + getFootersCount() + mAdapter.getItemCount();
        } else {
            return getHeadersCount() + getFootersCount();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;

        int type = ViewTypeSpec.getType(viewType);
        int value = ViewTypeSpec.getValue(viewType);

        if (type == ViewTypeSpec.HEADER) {
            viewHolder = getHeaderOrFooterViewHolder(mHeaderViewInfos, value);
        } else if (type == ViewTypeSpec.FOOTER) {
            viewHolder = getHeaderOrFooterViewHolder(mFooterViewInfos, value);
        } else {
            viewHolder = mAdapter.onCreateViewHolder(parent, viewType);
        }
        if (null == viewHolder) {
            viewHolder = mAdapter.onCreateViewHolder(parent, viewType);
        }
        return viewHolder;
    }

    private ViewHolder getHeaderOrFooterViewHolder(List<FixedViewInfo> fixedViewInfos, int value) {
        ViewHolder viewHolder = null;
        if (value >= 0 && value < fixedViewInfos.size()) {
            View view = fixedViewInfos.get(value).view;
            if (null != view) {
                viewHolder = new ViewHolder(view);
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            return;
        }

        int adjPosition = position - numHeaders;
        int adapterCount;

        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                mAdapter.onBindViewHolder(holder, adjPosition);
            }
        }

    }

    public int getHeadersCount() {
        return mHeaderViewInfos.size();
    }

    public int getFootersCount() {
        return mFooterViewInfos.size();
    }

    public boolean isEmpty() {
        return mAdapter == null || mAdapter.getItemCount() == 0;
    }

    public class FixedViewInfo {
        public int viewType;
        public View view;
        public Object data;
        public boolean isSelectable;
    }


    public void addHeaderView(View v, Object data, boolean isSelectable) {
        if (mHeaderViewInfos.add(newFixedViewInfo(v, data, isSelectable, ITEM_VIEW_TYPE_HEADER))) {
            notifyDataSetChanged();
        }
    }

    public void addHeaderView(View v) {
        addHeaderView(v, null, true);
    }

    public void addFooterView(View v, Object data, boolean isSelectable) {
        if (mFooterViewInfos.add(newFixedViewInfo(v, data, isSelectable, ITEM_VIEW_TYPE_FOOTER))) {
            notifyDataSetChanged();
        }
    }

    public void addFooterView(View v) {
        addFooterView(v, null, true);
    }

    private FixedViewInfo newFixedViewInfo(View v, Object data, boolean isSelectable, int viewType) {
        FixedViewInfo info = new FixedViewInfo();
        info.viewType = viewType;
        info.view = v;
        info.data = data;
        info.isSelectable = isSelectable;
        return info;
    }

    private boolean areAllListInfosSelectable(ArrayList<FixedViewInfo> infos) {
        if (infos != null) {
            for (FixedViewInfo info : infos) {
                if (!info.isSelectable) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean removeHeader(View v) {
        for (int i = 0; i < mHeaderViewInfos.size(); i++) {
            FixedViewInfo info = mHeaderViewInfos.get(i);
            if (info.view == v) {
                mHeaderViewInfos.remove(i);
                return true;
            }
        }

        return false;
    }

    public boolean removeFooter(View v) {
        for (int i = 0; i < mFooterViewInfos.size(); i++) {
            FixedViewInfo info = mFooterViewInfos.get(i);
            if (info.view == v) {
                mFooterViewInfos.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public RecyclerView.Adapter getWrappedAdapter() {
        return mAdapter;
    }

    static class ViewTypeSpec {

        static final int TYPE_SHIFT = 30;
        static final int TYPE_MASK = 0x3 << TYPE_SHIFT;

        public static final int HEADER = 1 << TYPE_SHIFT;
        public static final int FOOTER = 2 << TYPE_SHIFT;
        public static final int LOADER = 3 << TYPE_SHIFT;

        public static int makeItemViewTypeSpec(int value, int type) {
            return (value & ~TYPE_MASK) | (type & TYPE_MASK);
        }

        public static int getType(int viewType) {
            //noinspection ResourceType
            return (viewType & TYPE_MASK);
        }

        public static int getValue(int viewType) {
            return (viewType & ~TYPE_MASK);
        }
    }

}
