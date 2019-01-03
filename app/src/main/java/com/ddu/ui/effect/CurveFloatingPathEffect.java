package com.ddu.ui.effect;

import android.graphics.Path;
import androidx.annotation.NonNull;

import com.ddu.ui.view.FloatingPath;
import com.ddu.ui.view.FloatingPathEffect;
import com.ddu.ui.view.FloatingTextView;


/**
 * Author UFreedom
 */
public class CurveFloatingPathEffect implements FloatingPathEffect {


    @NonNull
    @Override
    public FloatingPath getFloatingPath(FloatingTextView floatingTextView) {
        Path path = new Path();
        path.moveTo(0, 0);
        path.quadTo(-100, -200, 0, -300);
        path.quadTo(200, -400, 0, -500);
        return FloatingPath.create(path, false);
    }

}
