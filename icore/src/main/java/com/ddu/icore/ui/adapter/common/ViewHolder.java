package com.ddu.icore.ui.adapter.common;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ddu.icore.util.sys.ViewUtils;

public class ViewHolder extends RecyclerView.ViewHolder {

    public View mConvertView;
    private SparseArray<View> mViews;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    @NonNull
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (null == view) {
            view = ViewUtils.findViewById(mConvertView, viewId);
            mViews.append(viewId, view);
        }
        return (T) view;
    }

    @NonNull
    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    @NonNull
    public ViewHolder setText(int viewId, int resId) {
        TextView tv = getView(viewId);
        tv.setText(resId);
        return this;
    }

    @NonNull
    public ViewHolder setText(int viewId, CharSequence charSequence) {
        TextView tv = getView(viewId);
        tv.setText(charSequence);
        return this;
    }

    @NonNull
    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    @NonNull
    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    @NonNull
    public ViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public ViewHolder setChecked(int viewId, boolean checked) {
        RadioButton radioButton = getView(viewId);
        radioButton.setChecked(checked);
        return this;
    }

    @NonNull
    public ViewHolder setBackgroud(int viewId, Drawable background) {
        View view = getView(viewId);
        view.setBackground(background);
        return this;
    }

    @NonNull
    public ViewHolder setBackgroud(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    @NonNull
    public ViewHolder setVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    @NonNull
    public ViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    @NonNull
    public ViewHolder setOnClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
        return this;
    }

}
