package com.ddu.ui.fragment.life;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ddu.R;
import com.ddu.icore.ui.adapter.common.DefaultRecycleViewAdapter;
import com.ddu.icore.ui.adapter.common.ViewHolder;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.ui.view.BottomView;
import com.ddu.ui.view.DividerItemDecoration;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by yzbzz on 16/4/21.
 */
public class LoanFragment extends DefaultFragment {

    @Nullable
    @BindView(R.id.rl_mortgage)
    RelativeLayout rlMortgage;
    @Nullable
    @BindView(R.id.btn_calculator)
    Button btnCalculator;
    @Nullable
    @BindView(R.id.pc_mortgage)
    PieChart mPieChart;
    @Nullable
    @BindView(R.id.ll_mortgage)
    LinearLayout llMortgage;
    @Nullable
    @BindView(R.id.tv_loan)
    TextView tvLoan;

    private List<String> mList;

    private Unbinder unbinder;

    @Override
    public void initData(Bundle savedInstanceState) {
        mList = new ArrayList<>();
        for (int i = 1; i < 30; i++) {
            mList.add(i + "年" + "(" + i * 12 + "期" + ")");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_life_mortgage_item;
    }

    @Override
    public void initView() {
        unbinder = ButterKnife.bind(this, mView);
        setDefaultTitle("房贷计算");
    }

    @NonNull
    protected String[] mParties = new String[]{
            "a", "b", "c", "LoginLogic"
    };

    public static final int[] COLORFUL_COLORS = {
            Color.rgb(193, 37, 82), Color.rgb(255, 102, 0), Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31), Color.rgb(179, 100, 53)
    };

    private void setData() {
        llMortgage.setVisibility(View.VISIBLE);
        mPieChart.setUsePercentValues(true);
        mPieChart.setDescription("Hello");
        mPieChart.setDrawHoleEnabled(false);
//        mPieChart.setDragDecelerationFrictionCoef(0.95f);

        ArrayList<Entry> yVals1 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            yVals1.add(new Entry(30, i));
        }

        ArrayList<String> xVals = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            xVals.add(mParties[i % mParties.length]);
        }

        ArrayList<Integer> colors = new ArrayList<>();
        for (int i = 0; i < COLORFUL_COLORS.length; i++) {
            colors.add(COLORFUL_COLORS[i]);
        }

        PieDataSet dataSet = new PieDataSet(yVals1, "Biu");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(colors);

//        PieData data = new PieData(xVals, dataSet);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(11f);
//        data.setValueTextColor(Color.WHITE);
//
//        mPieChart.setData(data);
//        mPieChart.highlightValue(null);
//
//        mPieChart.invalidate();
//
//
//        Legend l = mPieChart.getLegend();
//        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
////        l.setXEntrySpace(7f);
////        l.setYEntrySpace(0f);
//        l.setYOffset(10f);
    }


    @OnClick({R.id.rl_mortgage, R.id.btn_calculator})
    public void onClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.rl_mortgage:
                showBottomDialog();
                break;
            case R.id.btn_calculator:
                setData();
                break;
            default:
                break;
        }
    }


    private void showBottomDialog() {
        final BottomView dialog = new BottomView(baseActivity);
        final View view = LayoutInflater.from(baseActivity).inflate(R.layout.fragment_life_mortgage_bottom, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_bottom);
        recyclerView.setLayoutManager(new LinearLayoutManager(baseActivity));

        DefaultRecycleViewAdapter defaultRecycleViewAdapter = new DefaultRecycleViewAdapter<String>(baseActivity, mList) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.recyclerview_item_default;
            }

            @Override
            public void bindView(@NonNull ViewHolder viewHolder, final String data, int position) {
                viewHolder.setText(R.id.tv_detail, data);
                viewHolder.setOnClickListener(R.id.ll_detail, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismissBottomView();
                        tvLoan.setText(data);
                    }
                });
            }
        };
        recyclerView.setAdapter(defaultRecycleViewAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(baseActivity, DividerItemDecoration.VERTICAL_LIST));
        dialog.setContentView(view);
        dialog.setAnimation(R.style.BottomToTopAnim);
        dialog.showBottomView(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public boolean isShowTitleBar() {
        return false;
    }
}
