//package com.ddu.databinding;
//
//import android.databinding.BindingAdapter;
//import android.graphics.Color;
//import android.graphics.drawable.GradientDrawable;
//import android.view.View;
//
///**
// * Created by yzbzz on 2018/11/12.
// */
//public class BAdapters {
//
//    @BindingAdapter(value = {"bg_radius_b", "bg_color_s_b"}, requireAll = false)
//    public static void bindBackgrounds(View v, String radius, String bgColor) {
//        GradientDrawable gd = new GradientDrawable();
//
//        gd.setCornerRadius(Integer.parseInt(radius));
//
//        if (null != bgColor) {
//            gd.setColor(Color.parseColor(bgColor));
//        }
//        v.setBackground(gd);
//    }
//}
