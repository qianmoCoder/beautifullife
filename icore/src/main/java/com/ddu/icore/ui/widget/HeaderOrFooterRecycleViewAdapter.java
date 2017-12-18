package com.ddu.icore.ui.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ddu.icore.ui.adapter.common.ViewHolder;

import java.util.ArrayList;

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
            return mHeaderViewInfos.get(position).viewType;
        }

        int adjPosition = position - numHeaders;
        int adapterCount = 0;

        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemViewType(adjPosition);
            }
        }

        return mFooterViewInfos.get(adjPosition - adapterCount).viewType;
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
    public long getItemId(int position) {
        int numHeaders = getHeadersCount();
        if (mAdapter != null && position >= numHeaders) {
            int adjPosition = position - numHeaders;
            int adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemId(adjPosition);
            }
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder headerItemViewHolder = onCreateHeaderOrFooterItemViewHolder(parent, viewType, mHeaderViewInfos);
        if (null != headerItemViewHolder) {
            return headerItemViewHolder;
        }

        RecyclerView.ViewHolder footerItemViewHolder = onCreateHeaderOrFooterItemViewHolder(parent, viewType, mFooterViewInfos);
        if (null != footerItemViewHolder) {
            return footerItemViewHolder;
        }

        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    public RecyclerView.ViewHolder onCreateHeaderOrFooterItemViewHolder(ViewGroup parent, int viewType, ArrayList<FixedViewInfo> mViewList) {
        RecyclerView.ViewHolder viewHolder;
        View view = null;
        for (FixedViewInfo fixedViewInfo : mViewList) {
            if (fixedViewInfo.viewType == viewType) {
                view = fixedViewInfo.view;
                break;
            }
        }
        if (null == view) {
            viewHolder = null;
        } else {
            viewHolder = new ViewHolder(view);
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
        mHeaderViewInfos.add(newFixedViewInfo(v, data, isSelectable, ITEM_VIEW_TYPE_HEADER));
//        notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
    }

    public void addHeaderView(View v) {
        addHeaderView(v, null, true);
    }

    public void addFooterView(View v, Object data, boolean isSelectable) {
        mFooterViewInfos.add(newFixedViewInfo(v, data, isSelectable, ITEM_VIEW_TYPE_FOOTER));
//        notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
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

}
