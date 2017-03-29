package com.ddu.ui.view;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.NonNull;

/**
 * Author UFreedom
 * 
 */
public class FloatingPath {

    private Path mPath;
    private PathMeasure mPathMeasure;

    protected FloatingPath() {
        this.mPath = new Path();
    }
   
    protected FloatingPath(Path path) {
        this.mPath = path;
    }

    @NonNull
    public static FloatingPath create(Path path, boolean forceClose) {
        FloatingPath floatingPath = new FloatingPath(path);
        floatingPath.mPathMeasure = new PathMeasure(path, forceClose);
        return floatingPath;
    }

    public Path getPath() {
        return mPath;
    }

    public PathMeasure getPathMeasure() {
        return mPathMeasure;
    }
}
