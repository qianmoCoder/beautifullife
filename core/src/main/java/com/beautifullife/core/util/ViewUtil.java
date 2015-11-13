package com.beautifullife.core.util;

import android.app.Activity;
import android.view.View;

/**
 * Created by admin on 2015/9/24.
 */
public class ViewUtil {

    public static <T extends View> T findView(Activity context,int id){
       return (T) context.findViewById(id);
    }

    public static <T extends View> T findView(View rootView,int id){
        return (T) rootView.findViewById(id);
    }
}
