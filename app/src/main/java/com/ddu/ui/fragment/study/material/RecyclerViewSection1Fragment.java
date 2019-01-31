package com.ddu.ui.fragment.study.material;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ddu.R;
import com.ddu.icore.refresh.PullToRefreshBase;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.model.Performer;
import com.ddu.ui.adapter.PerformerListAdapter;
import com.ddu.widget.PinnedSectionRecyclerView;
import com.iannotation.IElement;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by yzbzz on 2017/3/31.
 */
@IElement("MD")
public class RecyclerViewSection1Fragment extends DefaultFragment {

    public static final String TAG = "ExampleActivity";

    private static final int[] COLORS = new int[]{
            R.color.green_light, R.color.orange_light,
            R.color.blue_light, R.color.red_light};

    private List<Item> mList = new ArrayList<>();

    MyAdapter myAdapter;

    PinnedSectionRecyclerView mPinnedRecyclerView;

    private static final int V_LISTVIEW = 0;

    private static final int V_GRIDVIEW = 2;

    protected SmartRefreshLayout mPullToRefreshScrollView;

    protected PinnedSectionRecyclerView mRvDefault;
    protected LinearLayoutManager mLinearLayoutManager;

    protected List<Performer> mDataEntities = new ArrayList<>();

    protected PerformerListAdapter mAdapter;

    protected PullToRefreshBase.Mode mMode;
    protected RecyclerView.ItemDecoration mItemDecoration;

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_pinned_section;
    }

    @Override
    public void initView() {

        mPullToRefreshScrollView = findViewById(R.id.default_refresh_view);

        mLinearLayoutManager = new LinearLayoutManager(getMContext(), RecyclerView.VERTICAL, false);
        mPinnedRecyclerView = findViewById(R.id.rl_default);
        mPinnedRecyclerView.setOnPinnedSectionTouchListener(mOnPinnedSectionTouchListener);

        mPinnedRecyclerView.setLayoutManager(mLinearLayoutManager);

        genData('A', 'Z');

        myAdapter = new MyAdapter();

        mPinnedRecyclerView.setAdapter(myAdapter);


        mPullToRefreshScrollView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(2000);
            }
        });
        mPullToRefreshScrollView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000);
            }
        });
    }

    private void genData(char from, char to) {

        mList.clear();
        final int sectionsNumber = to - from + 1;

        int sectionPosition = 0, listPosition = 0;
        int preSectionPosition = -1;
        for (char i = 0; i < sectionsNumber; i++) {
            Item section = new Item(Item.SECTION, String.valueOf((char) (from + i)));
            section.sectionPosition = sectionPosition;
            section.listPosition = listPosition++;
            if (preSectionPosition > -1) {
                Item preSection = mList.get(preSectionPosition);
                preSection.nextSectionPosition = sectionPosition;
            }
            mList.add(section);

            final int itemsNumber = (int) Math.abs((Math.cos(2f * Math.PI / 3f * sectionsNumber / (i + 1f)) * 25f));
            for (int j = 0; j < itemsNumber; j++) {
                Item item = new Item(Item.ITEM, section.text.toUpperCase(Locale.ENGLISH) + " - " + j);
                item.sectionPosition = sectionPosition;
                item.listPosition = listPosition++;
                mList.add(item);
            }
            preSectionPosition = sectionPosition;
            sectionPosition = listPosition;
        }
    }

    Toast mToast;

    private void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }

        mToast.show();

    }

    PinnedSectionRecyclerView.OnPinnedSectionTouchListener mOnPinnedSectionTouchListener = new PinnedSectionRecyclerView.OnPinnedSectionTouchListener() {
        @Override
        public void onClick(View pinnedItemView, int position) {
            showToast("click: " + mList.get(position).text);
        }

        @Override
        public void onLongClick(View pinnedItemView, int position) {
            showToast("longClick: " + mList.get(position).text);
        }
    };

    View.OnClickListener mItemOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Integer pos = (Integer) v.getTag();
            showToast("click: " + mList.get(pos).text);
        }
    };

    View.OnLongClickListener mItemOnLongClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            Integer pos = (Integer) v.getTag();
            showToast("longClick: " + mList.get(pos).text);
            return true;
        }
    };

    public RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(getMContext(), DividerItemDecoration.VERTICAL);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements PinnedSectionRecyclerView.Adapter {

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            MyAdapter.MyViewHolder myViewHolder = new MyAdapter.MyViewHolder(itemView, mItemOnClickListener, mItemOnLongClickListener);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
            Item item = mList.get(position);

            if (item.type == Item.SECTION) {
                //view.setOnClickListener(PinnedSectionListActivity.this);
                int color = holder.itemView.getContext().getResources().getColor(COLORS[item.sectionPosition % COLORS.length]);
                holder.mTextView.setBackgroundColor(color);
            }
            holder.mTextView.setText(mList.get(position).text);
            holder.itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return mList.get(position).type;
        }

        @Override
        public boolean isPinnedSectionItem(int position) {
            return getItemViewType(position) == Item.SECTION;
        }

        @Override
        public int findSectionPosition(int position) {
            return mList.get(position).sectionPosition;
        }

        @Override
        public int findNextSectionPosition(int position) {
            Log.d(TAG, "findNextSectionPosition: " + position + " , " + mList.get(position).toString());
            return mList.get(position).nextSectionPosition;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView mTextView;

            public MyViewHolder(View itemView, View.OnClickListener itemOnClickListener, View.OnLongClickListener itemOnLongClickListener) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.tv);
                itemView.setOnClickListener(itemOnClickListener);
                itemView.setOnLongClickListener(itemOnLongClickListener);
            }

        }
    }

    static class Item {

        public static final int ITEM = 0;
        public static final int SECTION = 1;

        public final int type;
        public final String text;

        public int sectionPosition;
        public int nextSectionPosition = -1;
        public int listPosition;

        public Item(int type, String text) {
            this.type = type;
            this.text = text;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "type=" + type +
                    ", text='" + text + '\'' +
                    ", sectionPosition=" + sectionPosition +
                    ", nextSectionPosition=" + nextSectionPosition +
                    ", listPosition=" + listPosition +
                    '}';
        }
    }
}
