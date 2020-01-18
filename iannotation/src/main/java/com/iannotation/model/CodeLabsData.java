package com.iannotation.model;

/**
 * Created by yzbzz on 2018/8/14.
 */
public class CodeLabsData implements Comparable<CodeLabsData> {

    private String path;
    private String parentId;
    private String parentContent;
    private String id;
    private String content;
    private String classType; // 0.Fragment 1.Activity

    private Class<?> cls;
    private String className;

    private CodeLabsData(CodeLabsData data) {
        this.path = data.path;
        this.parentId = data.parentId;
        this.parentContent = data.parentContent;
        this.id = data.id;
        this.content = data.content;
        this.cls = data.cls;
        this.className = cls.getName();
        this.classType = data.classType;
    }

    private CodeLabsData(String path, String parentId, String parentContent, String id, String content, Class<?> cls, String classType) {
        this.path = path;
        this.parentId = parentId;
        this.parentContent = parentContent;
        this.id = id;
        this.content = content;
        this.cls = cls;
        this.className = cls.getName();
        this.classType = classType;
    }

    public static CodeLabsData build(CodeLabsData routeMeta) {
        return new CodeLabsData(routeMeta);
    }

    public static CodeLabsData build(String path, String parentId, String parentContent, String id, String content, Class<?> cls, String classType) {
        return new CodeLabsData(path, parentId, parentContent, id, content, cls, classType);
    }

    public String getPath() {
        return path == null ? "" : path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParentId() {
        return parentId == null ? "" : parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentContent() {
        return parentContent == null ? "" : parentContent;
    }

    public void setParentContent(String parentContent) {
        this.parentContent = parentContent;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    public String getClassName() {
        return className == null ? "" : className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    @Override
    public int compareTo(CodeLabsData old) {
        int result1 = parentId.compareTo(old.parentId);
        int result2 = id.compareTo(old.id);
        return result1 == 0 ? result2 : result1;
    }
}
