package com.ddu.db.entity;

import android.support.annotation.NonNull;

/**
 * Created by yzbzz on 2017/5/12.
 */

public class ItemEntity implements Comparable<ItemEntity> {

    private String path;
    private String color;
    private String title;
    private String description;
    private String className;

    public String getPath() {
        return path == null ? "" : path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getColor() {
        return color == null ? "" : color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public void setDescription(String description) {
        this.description = description;
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
