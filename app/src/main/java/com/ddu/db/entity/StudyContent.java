package com.ddu.db.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by yzbzz on 2018/1/17.
 */
@Entity
public class StudyContent {
    public @Id long id;
    public String title;
    public String description;
    public String type;
    public boolean isOld;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isOld() {
        return isOld;
    }

    public void setOld(boolean old) {
        isOld = old;
    }
}
