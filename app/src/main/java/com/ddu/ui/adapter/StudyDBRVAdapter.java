package com.ddu.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ddu.R;
import com.ddu.databinding.FragmentStudyDbRvItemBinding;
import com.ddu.icore.callback.Consumer1;
import com.ddu.viewmodels.StudyViewModel;
import com.iannotation.model.RouteMeta;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class StudyDBRVAdapter extends ListAdapter<RouteMeta, StudyDBRVAdapter.ViewHolder> {

    private Consumer1<RouteMeta> consumer1;

    public StudyDBRVAdapter() {
        super(new StudyDiffCallback());
    }


    public void setItemClickListener(Consumer1<RouteMeta> consumer1) {
        this.consumer1 = consumer1;
    }

    public View.OnClickListener createOnClickListener(final RouteMeta routeMeta) {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (consumer1 != null) {
                    consumer1.accept(routeMeta);
                }
            }
        };
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentStudyDbRvItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.fragment_study_db_rv_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final RouteMeta routeMeta = getItem(position);
        holder.itemView.setTag(routeMeta);
        holder.bind(createOnClickListener(routeMeta), routeMeta);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private FragmentStudyDbRvItemBinding binding;

        public ViewHolder(FragmentStudyDbRvItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(View.OnClickListener clickListener, RouteMeta routeMeta) {
            StudyViewModel studyViewModel = new StudyViewModel(itemView.getContext(), routeMeta);
            binding.setClickListener(clickListener);
            binding.setViewModel(studyViewModel);
            binding.executePendingBindings();
        }
    }

}
