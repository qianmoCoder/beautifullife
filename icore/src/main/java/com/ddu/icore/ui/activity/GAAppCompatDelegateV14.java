//package com.ddu.icore.ui.activity;
//
//import android.content.Context;
//import android.support.v7.app.AppCompatCallback;
//import android.support.v7.app.AppCompatDelegateImplV14;
//import android.util.AttributeSet;
//import android.view.View;
//import android.view.Window;
//
//import com.ddu.icore.ui.view.NovaTextView;
//
///**
// * Created by yzbzz on 2017/8/28.
// */
//
//public class GAAppCompatDelegateV14 extends AppCompatDelegateImplV14 {
//
//    GAAppCompatDelegateV14(Context context, Window window, AppCompatCallback callback) {
//        super(context, window, callback);
//    }
//
//    @Override
//    View callActivityOnCreateView(View parent, String name, Context context, AttributeSet attrs) {
//        switch (name) {
//            case "TextView":
//                return new NovaTextView(context);
//        }
//        return super.callActivityOnCreateView(parent, name, context, attrs);
//    }
//
//}
