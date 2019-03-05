package com.ddu.ui.fragment.study.material;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.model.Entity;
import com.ddu.model.Performer;
import com.ddu.ui.adapter.SectionAdapter;
import com.ddu.ui.helper.OnValueChangeListener;
import com.ddu.ui.view.StickyHeaderListView;
import com.google.gson.Gson;
import com.iannotation.IElement;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzbzz on 2017/3/31.
 */
@IElement("MD")
public class ListViewSectionFragment extends DefaultFragment {

    protected SmartRefreshLayout mPullToRefreshScrollView;

    protected List<Performer> mDataEntities = new ArrayList<>();

    private StickyHeaderListView mListView;
    private SectionAdapter mAdapter;
    private CheckBox mCheckAll;

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_lv_section;
    }

    @Override
    public void initView() {

        mPullToRefreshScrollView = findViewById(R.id.pl_refresh);

        Entity row = new Gson().fromJson(json, Entity.class);
        mListView = findViewById(R.id.lv);
        mListView.setAdapter(mAdapter = new SectionAdapter(getContext(), row.rows));

        mAdapter.setOnValueChangedListener(new OnValueChangeListener() {
            @Override
            public void onChange(int totalCount, double totalAmount, boolean isCheckAll) {
                BigDecimal bd = new BigDecimal(totalAmount).setScale(2, RoundingMode.UP);
                ((TextView) findViewById(R.id.tv_desc)).setText(totalCount + "个行程，共" + bd.doubleValue() + "元");
                // 防止调用onCheckedChanged
                mCheckAll.setOnCheckedChangeListener(null);
                mCheckAll.setChecked(isCheckAll);
                mCheckAll.setOnCheckedChangeListener(onCheckedChangeListener);
            }
        });

        initChechBox();


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

    private void initChechBox() {
        mCheckAll = (CheckBox) findViewById(R.id.cb_check_all);
        mCheckAll.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mAdapter.setCheckAll(isChecked);
        }
    };

    String json = "{\n" +
            "    \"rows\":[\n" +
            "        {\n" +
            "            \"timestamp\":1484060400,\n" +
            "            \"src\":\"杭州-茂源大厦\",\n" +
            "            \"dest\":\"杭州-高新软件园\",\n" +
            "            \"amount\":24.01\n" +
            "        },\n" +
            "        {\n" +
            "            \"timestamp\":1483974000,\n" +
            "            \"src\":\"杭州-茂源大厦\",\n" +
            "            \"dest\":\"杭州-高新软件园\",\n" +
            "            \"amount\":24.01\n" +
            "        },\n" +
            "        {\n" +
            "            \"timestamp\":1483887600,\n" +
            "            \"src\":\"杭州-茂源大厦\",\n" +
            "            \"dest\":\"杭州-高新软件园\",\n" +
            "            \"amount\":24.01\n" +
            "        },\n" +
            "        {\n" +
            "            \"timestamp\":1483628400,\n" +
            "            \"src\":\"杭州-茂源大厦\",\n" +
            "            \"dest\":\"杭州-高新软件园\",\n" +
            "            \"amount\":24.01\n" +
            "        },\n" +
            "        {\n" +
            "            \"timestamp\":1481814000,\n" +
            "            \"src\":\"杭州-阿里巴巴滨江园区\",\n" +
            "            \"dest\":\"杭州-高新软件园\",\n" +
            "            \"amount\":25.00\n" +
            "        },\n" +
            "        {\n" +
            "            \"timestamp\":1479222000,\n" +
            "            \"src\":\"杭州-茂源大厦\",\n" +
            "            \"dest\":\"杭州-阿里巴巴滨江园区\",\n" +
            "            \"amount\":28.01\n" +
            "        },\n" +
            "        {\n" +
            "            \"timestamp\":1477407600,\n" +
            "            \"src\":\"杭州-龙翔桥\",\n" +
            "            \"dest\":\"杭州-定安路\",\n" +
            "            \"amount\":19.20\n" +
            "        },\n" +
            "        {\n" +
            "            \"timestamp\":1475039023,\n" +
            "            \"src\":\"杭州-茂源大厦\",\n" +
            "            \"dest\":\"杭州-高新软件园\",\n" +
            "            \"amount\":24.01\n" +
            "        },\n" +
            "        {\n" +
            "            \"timestamp\":1474779823,\n" +
            "            \"src\":\"杭州-茂源大厦\",\n" +
            "            \"dest\":\"杭州-高新软件园\",\n" +
            "            \"amount\":24.01\n" +
            "        },\n" +
            "        {\n" +
            "            \"timestamp\":1474347823,\n" +
            "            \"src\":\"杭州-武林广场\",\n" +
            "            \"dest\":\"杭州-西湖文化广场\",\n" +
            "            \"amount\":24.10\n" +
            "        },\n" +
            "        {\n" +
            "            \"timestamp\":1468991023,\n" +
            "            \"src\":\"杭州-星光大道\",\n" +
            "            \"dest\":\"杭州-滨康路\",\n" +
            "            \"amount\":23.57\n" +
            "        },\n" +
            "        {\n" +
            "            \"timestamp\":1466399143,\n" +
            "            \"src\":\"杭州-AppStore体验店\",\n" +
            "            \"dest\":\"杭州-高新软件园\",\n" +
            "            \"amount\":20\n" +
            "        },\n" +
            "        {\n" +
            "            \"timestamp\":1465967143,\n" +
            "            \"src\":\"杭州-茂源大厦\",\n" +
            "            \"dest\":\"杭州-西湖\",\n" +
            "            \"amount\":81.07\n" +
            "        },\n" +
            "        {\n" +
            "            \"timestamp\":1465362343,\n" +
            "            \"src\":\"杭州-星光大道\",\n" +
            "            \"dest\":\"杭州-滨康路\",\n" +
            "            \"amount\":23.57\n" +
            "        },\n" +
            "        {\n" +
            "            \"timestamp\":1465275943,\n" +
            "            \"src\":\"杭州-AppStore体验店\",\n" +
            "            \"dest\":\"杭州-高新软件园\",\n" +
            "            \"amount\":20\n" +
            "        },\n" +
            "        {\n" +
            "            \"timestamp\":1465103143,\n" +
            "            \"src\":\"杭州-茂源大厦\",\n" +
            "            \"dest\":\"杭州-西湖\",\n" +
            "            \"amount\":81.07\n" +
            "        },\n" +
            "        {\n" +
            "            \"timestamp\":1462424743,\n" +
            "            \"src\":\"杭州-星光大道\",\n" +
            "            \"dest\":\"杭州-滨康路\",\n" +
            "            \"amount\":23.57\n" +
            "        },\n" +
            "        {\n" +
            "            \"timestamp\":1462125943,\n" +
            "            \"src\":\"杭州-AppStore体验店\",\n" +
            "            \"dest\":\"杭州-高新软件园\",\n" +
            "            \"amount\":20\n" +
            "        },\n" +
            "        {\n" +
            "            \"timestamp\":1462035943,\n" +
            "            \"src\":\"杭州-茂源大厦\",\n" +
            "            \"dest\":\"杭州-西湖\",\n" +
            "            \"amount\":81.07\n" +
            "        }\n" +
            "    ]\n" +
            "}";

}
