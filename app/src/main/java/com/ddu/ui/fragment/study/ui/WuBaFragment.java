package com.ddu.ui.fragment.study.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.ui.helper.WuBaBehavior;
import com.iannotation.IElement;

/**
 * Created by lhz on 16/5/6.
 */
@IElement("UI")
public class WuBaFragment extends DefaultFragment {

    private RecyclerView recyclerView;

    @NonNull
    public static WuBaFragment newInstance(String taskId) {
        Bundle arguments = new Bundle();
        arguments.putString("ARGUMENT_TASK_ID", taskId);
        WuBaFragment fragment = new WuBaFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {


    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wuba;
    }

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ViewHolder(LayoutInflater.from(getMContext()).inflate(R.layout.fragment_home_item, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ViewHolder vh = (ViewHolder) holder;
                vh.text.setText("  Item " + (position + 1));
            }

            @Override
            public int getItemCount() {
                return 3;
            }

            class ViewHolder extends RecyclerView.ViewHolder {

                TextView text;

                public ViewHolder(View itemView) {
                    super(itemView);

                    text = (TextView) itemView.findViewById(R.id.tv_name);
                }

            }
        });

        final WuBaBehavior myBehavior = new WuBaBehavior(getMContext());
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) recyclerView.getLayoutParams();
        params.setBehavior(myBehavior);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean isShowTitleBar() {
        return false;
    }
}
