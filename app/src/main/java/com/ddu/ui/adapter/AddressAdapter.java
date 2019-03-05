package com.ddu.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.ddu.R;
import com.ddu.icore.ui.adapter.common.DefaultRVAdapter;
import com.ddu.icore.ui.adapter.common.ViewHolder;
import com.ddu.model.AddressBean;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class AddressAdapter extends DefaultRVAdapter<AddressBean> {

    private OnItemClickListener mOnClickListener;
    private AddressAdapter provinceAdapter;

    public AddressAdapter(Context context, List<AddressBean> items) {
        super(context, items);
        provinceAdapter = this;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_address;
    }

    @Override
    public void bindView(ViewHolder viewHolder, AddressBean data, final int position) {
        viewHolder.setText(R.id.textview, data.getLabel());
        viewHolder.setTextColor(R.id.textview, data.isStatus() ? Color.parseColor("#4897fa") : Color.parseColor("#444444"));
        viewHolder.setVisibility(R.id.iv_payment_hook, data.isStatus() ? View.VISIBLE : View.INVISIBLE);
        if (mOnClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    mOnClickListener.onItemClick(provinceAdapter, v, position);
                }
            });
        }

    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(RecyclerView.Adapter adapter, View view, int position);
    }

}
