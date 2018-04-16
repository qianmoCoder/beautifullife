package com.ddu.ui.fragment.study.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;
import android.support.v7.graphics.Target;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ddu.R;
import com.ddu.icore.ui.adapter.common.DefaultRecycleViewAdapter;
import com.ddu.icore.ui.adapter.common.ViewHolder;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.ui.view.DividerItemDecoration;
import com.iannotation.ContentType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzbzz on 2017/9/11.
 */
@ContentType("UI")
public class PaletteFragment extends DefaultFragment implements View.OnClickListener {

    private ImageView ivBg;
    private Button btnNext;
    private RecyclerView rvSwatch;
    int index = 0;

    private List<Palette.Swatch> swatchList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ui_palette;
    }

    @Override
    public void initView() {
        ivBg = findViewById(R.id.iv_bg);
        btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);

        rvSwatch = findViewById(R.id.rv_swatch);
        rvSwatch.setAdapter(new DefaultRecycleViewAdapter<Palette.Swatch>(getMContext(), swatchList) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.recyclerview_item_default;
            }

            @Override
            public void bindView(ViewHolder viewHolder, Palette.Swatch data, int position) {
                viewHolder.setText(R.id.tv_detail, "hello, swatch");
                viewHolder.setBackgroud(R.id.tv_detail, data.getRgb());
            }
        });
        rvSwatch.setLayoutManager(new LinearLayoutManager(getMContext(),LinearLayoutManager.VERTICAL,false));
        rvSwatch.addItemDecoration(new DividerItemDecoration(getMContext(), DividerItemDecoration.HORIZONTAL));
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_next:
                showNext();
                break;
        }
    }

    public void showNext() {
        swatchList.clear();
        index++;
        if (index > 6) {
            index = 0;
        }
        int resId = getResId("ic_test_" + index, R.drawable.class);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
        Palette.Builder builder = Palette.from(bitmap);
        Palette palette = builder.generate();
        List<Palette.Swatch> swatches = palette.getSwatches();
        List<Target> targets = palette.getTargets();
        swatchList.addAll(swatches);
        rvSwatch.getAdapter().notifyDataSetChanged();
        ivBg.setImageResource(resId);
    }

    public static int getResId(String variableName, @NonNull Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
