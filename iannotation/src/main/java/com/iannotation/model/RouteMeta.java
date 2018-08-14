package com.iannotation.model;

/**
 * Created by yzbzz on 2018/8/14.
 */
public class RouteMeta implements Comparable<RouteMeta> {

    private String path;
    private String text;
    private String color;
    private String description;
    private Class<?> cls;

    public RouteMeta() {

    }

    public RouteMeta(RouteMeta meta) {
        this.path = meta.path;
        this.text = meta.text;
        this.color = meta.color;
        this.description = meta.description;
        this.cls = meta.cls;
    }


    public RouteMeta(String path, String text, String color, String description, Class<?> cls) {
        this.path = path;
        this.text = text;
        this.color = color;
        this.description = description;
        this.cls = cls;
    }

    public static RouteMeta build(RouteMeta routeMeta) {
        return new RouteMeta(routeMeta);
    }

    public static RouteMeta build(String path, String text, String color, String description, Class<?> cls) {
        return new RouteMeta(path, text, color, description, cls);
    }

    public String getPath() {
        return path == null ? "" : path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getText() {
        return text == null ? "" : text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color == null ? "" : color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    @Override
    public int compareTo(RouteMeta old) {
        return text.compareTo(old.text);
    }
}
