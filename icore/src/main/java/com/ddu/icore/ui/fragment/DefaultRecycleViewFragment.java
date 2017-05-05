package com.ddu.icore.ui.fragment;

import com.alibaba.fastjson.JSONObject;
import com.ddu.icore.R;
import com.ddu.icore.entity.BaseEntity;
import com.ddu.icore.ui.adapter.common.DefaultRecycleViewAdapter;
import com.ddu.icore.util.ToastUtils;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by yzbzz on 2017/4/19.
 */

public abstract class DefaultRecycleViewFragment<E extends BaseEntity, D, A extends DefaultRecycleViewAdapter> extends AbstractRecycleViewFragment<D, A> {

    protected E mEntity;

    public void setDefaultData(String result) {
        mEntity = JSONObject.parseObject(result, getType(0));
        if (null != mEntity) {
            if (mEntity.isSuccess()) {
                ToastUtils.showTextToast(mEntity.getMessage());
            } else {
                List<D> dataEntities = getEntityData();
                mDataEntities.clear();
                if (null != dataEntities && !dataEntities.isEmpty()) {
                    mDataEntities.addAll(dataEntities);
                }
                mAdapter.setEmptyView(getEmptyViewId(), mRvDefault);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    public abstract List<D> getEntityData();

    public int getEmptyViewId() {
        return R.layout.fragment_record_empty;
    }

    public Class<E> getType(int index) {
        Class<E> cls = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[index];
        return cls;
    }
}
