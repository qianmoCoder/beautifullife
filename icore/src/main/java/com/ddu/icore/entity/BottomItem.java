package com.ddu.icore.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by yzbzz on 2018/8/8.
 */
public class BottomItem {

    private int id;
    private String title;
    private Drawable icon;

    public BottomItem() {
    }

    public BottomItem(int id, String title, Drawable icon) {
        this.id = id;
        this.title = title;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
