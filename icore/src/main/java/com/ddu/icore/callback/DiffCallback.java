package com.ddu.icore.callback;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by yzbzz on 2017/4/19.
 */

public class DiffCallback<T> extends DiffUtil.Callback {

//    DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffCallback(this.persons, newList));
//        diffResult.dispatchUpdatesTo(this);

    List<T> oldData;
    List<T> newData;

    public DiffCallback(List<T> newPersons, List<T> oldPersons) {
        this.oldData = newPersons;
        this.newData = oldPersons;
    }

    @Override
    public int getOldListSize() {
        return oldData.size();
    }

    @Override
    public int getNewListSize() {
        return newData.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
//        return oldPersons.get(oldItemPosition).id == newPersons.get(newItemPosition).id;
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
//        return oldPersons.get(oldItemPosition).equals(newPersons.get(newItemPosition));
        //        TestBean beanOld = mOldDatas.get(oldItemPosition);
//        TestBean beanNew = mNewDatas.get(newItemPosition);
//        if (!beanOld.getDesc().equals(beanNew.getDesc())) {
//            return false;//如果有内容不同，就返回false
//        }
//        if (beanOld.getPic() != beanNew.getPic()) {
//            return false;//如果有内容不同，就返回false
//        }
//        return true; //默认两个data内容是相同的
        return false;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
