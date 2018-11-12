package com.ddu.ui.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ddu.databinding.FragmentStudyContentRvItemDbBinding;
import com.ddu.databinding.FragmentStudyDbRvItemBinding;
import com.ddu.icore.callback.Consumer1;
import com.iannotation.model.RouteMeta;

public class StudyDBRVAdapter extends ListAdapter<RouteMeta, StudyDBRVAdapter.ViewHolder> {

    private Consumer1<RouteMeta> consumer1;
    private int radius;

    protected StudyDBRVAdapter() {
        super(new StudyDiffCallback());
    }

//    public StudyDBRVAdapter(Context context, List<RouteMeta> items) {
//        super(context, items);
//        radius = context.getResources().getDimensionPixelSize(R.dimen.dp_5);
//    }
//
//    @Override
//    public int getLayoutId(int viewType) {
//        return R.layout.fragment_study_rv_item;
//    }
//
//    @Override
//    public void bindView(ViewHolder viewHolder, final RouteMeta data, final int position) {
//        TextView tvTitle = viewHolder.getView(R.id.tv_title);
//        tvTitle.setText(data.getText());
//
//        ShapeInject.inject(tvTitle)
//                .setRadius(radius)
//                .setBackgroundColor(getColor(data.getColor()))
//                .background();
//
//        viewHolder.setText(R.id.tv_description, data.getDescription());
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (null != consumer1) {
//                    consumer1.accept(data);
//                }
//            }
//        });
//    }

    private int getColor(String colorStr) {
        int color;
        try {
            color = Color.parseColor(colorStr);
        } catch (Exception e) {
            color = Color.BLUE;
        }
        return color;
    }


    public void setItemClickListener(Consumer1<RouteMeta> consumer1) {
        this.consumer1 = consumer1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new ViewHolder();
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FragmentStudyDbRvItemBinding d;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(FragmentStudyContentRvItemDbBinding binding) {
//            super(binding.);
//            super(binding.);
//            super(binding.root);
            super(null);
        }
    }

}
