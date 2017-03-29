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

package com.ddu.ui.adapter;

//public class StudyRecycleViewAdapter extends DefaultRecycleViewAdapter<StudyContent> implements ItemTouchHelperAdapter {
//
//    private String title;
//
//    private OnStartDragListener mDragStartListener;
//
//    public StudyRecycleViewAdapter(Context context, List<StudyContent> items, String title, OnStartDragListener dragStartListener) {
//        super(context, items);
//        this.title = title;
//        mDragStartListener = dragStartListener;
//    }
//
//    @Override
//    public int getLayoutId(int viewType) {
//        return R.layout.fragment_study_list_item;
//    }
//
//    @Override
//    public void bindView(@NonNull ViewHolder viewHolder, @NonNull StudyContent data, int position) {
//        final StudyContent studyContent = data;
//        viewHolder.setText(R.id.tv_title, data.getTitle());
//        ExpandableTextView expandableTextView = viewHolder.getView(R.id.expand_text_view);
//        expandableTextView.setText(data.getDescription());
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String type = studyContent.getType().trim();
//                if (type.equalsIgnoreCase("UI")) {
//                    FragmentUtils.startFragment(mContext, UIFragment.class);
//                } else if (type.equalsIgnoreCase("Glide")) {
//                }
//            }
//        });
//    }
//
//
//    @Override
//    public boolean onItemMove(int fromPosition, int toPosition) {
//        Collections.swap(mItems, fromPosition, toPosition);
//        notifyItemMoved(fromPosition, toPosition);
//        return true;
//    }
//
//    @Override
//    public void onItemDismiss(int position) {
//        mItems.remove(position);
//        notifyItemRemoved(position);
//    }
//
//}
