package com.ddu.db.entity;

import android.support.annotation.NonNull;

/**
 * Created by yzbzz on 2017/5/12.
 */

public class ItemEntity implements Comparable<ItemEntity> {

    private String title;
    private String className;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public int compareTo(@NonNull ItemEntity old) {
        return title.compareTo(old.title);
    }
}
