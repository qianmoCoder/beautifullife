package com.ddu.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yzbzz on 2017/5/9.
 */
@Entity
public class StudyContent {

    @Id
    private Long id;
    private String title;
    private String description;
    private String type;
    private boolean isOld;
    @Generated(hash = 1934119038)
    public StudyContent(Long id, String title, String description, String type,
            boolean isOld) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.isOld = isOld;
    }
    @Generated(hash = 1184820481)
    public StudyContent() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public boolean getIsOld() {
        return this.isOld;
    }
    public void setIsOld(boolean isOld) {
        this.isOld = isOld;
    }
}
