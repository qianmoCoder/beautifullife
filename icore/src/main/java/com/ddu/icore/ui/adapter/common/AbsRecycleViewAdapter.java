/*
 * Copyright (C) 2014 Lucas Rocha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ddu.icore.ui.adapter.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.LongSparseArray;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class AbsRecycleViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_VIEW_TYPE_EMPTY = -1;
    public static final int ITEM_VIEW_TYPE_HEADER = -2;
    public static final int ITEM_VIEW_TYPE_FOOTER = -3;

    public static final int CHOICE_MODE_NONE = 0;
    public static final int CHOICE_MODE_SINGLE = 1;
    public static final int CHOICE_MODE_MULTIPLE = 2;

    int mChoiceMode = CHOICE_MODE_NONE;

    int mCheckedItemCount;

    SparseBooleanArray mCheckStates;
    LongSparseArray<Integer> mCheckedIdStates;
    OnItemClickListener mOnItemClickListener;

    protected final Context mContext;
    protected LayoutInflater mLayoutInflater;

    private View mEmptyView;

    @NonNull
    protected final List<T> mItems;

    public AbsRecycleViewAdapter(Context context, @Nullable List<T> items) {
        mContext = context;
        mItems = (items != null) ? items : new ArrayList<T>();
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (isShowEmptyView()) {
            view = mEmptyView;
        } else {
            view = getView(parent, viewType);
        }
        RecyclerView.ViewHolder holder = getViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isShowEmptyView()) {
            return;
        }
        bindView(holder, mItems.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (isShowEmptyView()) {
            return 1;
        }
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowEmptyView()) {
            return ITEM_VIEW_TYPE_EMPTY;
        }
        return super.getItemViewType(position);
    }


    public View getEmptyView() {
        return mEmptyView;
    }

    public void setEmptyView(View mEmptyView) {
        this.mEmptyView = mEmptyView;
    }

    public void setEmptyView(int resId, ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resId, viewGroup, false);
        setEmptyView(view);
    }

    public boolean isShowEmptyView() {
        if (null == mEmptyView) {
            return false;
        }
        if (mItems.size() != 0) {
            return false;
        }
        return true;
    }

    public void clearChoices() {
        if (mCheckStates != null) {
            mCheckStates.clear();
        }
        if (mCheckedIdStates != null) {
            mCheckedIdStates.clear();
        }
        mCheckedItemCount = 0;
    }

    public int getChoiceMode() {
        return mChoiceMode;
    }

    public void setChoiceMode(int choiceMode) {
        mChoiceMode = choiceMode;
        if (mChoiceMode != CHOICE_MODE_NONE) {
            if (mCheckStates == null) {
                mCheckStates = new SparseBooleanArray(0);
            }
            if (null == mCheckedIdStates && hasStableIds()) {
                mCheckedIdStates = new LongSparseArray<>(0);
            }
        }
    }

    public boolean performItemClick(int position) {
        boolean handled = false;
        final boolean result;

        if (mChoiceMode != CHOICE_MODE_NONE) {
            handled = true;
            boolean checkedStateChanged = false;

            if (mChoiceMode == CHOICE_MODE_MULTIPLE) {
                boolean checked = !mCheckStates.get(position, false);
                mCheckStates.put(position, checked);
                if (null != mCheckedIdStates && hasStableIds()) {
                    long itemId = getItemId(position);
                    if (checked) {
                        mCheckedIdStates.put(itemId, position);
                    } else {
                        mCheckedIdStates.delete(itemId);
                    }
                }
                if (checked) {
                    mCheckedItemCount++;
                } else {
                    mCheckedItemCount--;
                }

                checkedStateChanged = true;
            } else if (mChoiceMode == CHOICE_MODE_SINGLE) {
                boolean checked = !mCheckStates.get(position, false);
                if (checked) {
                    mCheckStates.clear();
                    mCheckStates.put(position, true);
                    if (mCheckedIdStates != null && hasStableIds()) {
                        mCheckedIdStates.clear();
                        mCheckedIdStates.put(getItemId(position), position);
                    }
                    mCheckedItemCount = 1;
                } else if (mCheckStates.size() == 0 || !mCheckStates.valueAt(0)) {
                    mCheckedItemCount = 0;
                }
                checkedStateChanged = true;
            }
            if (checkedStateChanged) {
                // updateOnScreenCheckedViews;
            }
        }


        if (null != mOnItemClickListener) {
            mOnItemClickListener.onItemClick();
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    private void updateOnScreenCheckedViews() {
//        final int firstPos = mFirstPosition;
//        final int count = getChildCount();
//        final boolean useActivated = getContext().getApplicationInfo().targetSdkVersion
//                >= android.os.Build.VERSION_CODES.HONEYCOMB;
//        for (int i = 0; i < count; i++) {
//            final View child = getChildAt(i);
//            final int position = firstPos + i;
//
//            if (child instanceof Checkable) {
//                ((Checkable) child).setChecked(mCheckStates.get(position));
//            } else if (useActivated) {
//                child.setActivated(mCheckStates.get(position));
//            }
//        }
    }


    public interface OnItemClickListener {
        void onItemClick();
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public abstract View getView(ViewGroup parent, int viewType);

    @NonNull
    public abstract RecyclerView.ViewHolder getViewHolder(View view);

    public abstract void bindView(RecyclerView.ViewHolder holder, T data, int position);
}
