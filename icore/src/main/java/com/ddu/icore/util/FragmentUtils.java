package com.ddu.icore.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by liuhongzhe on 16/7/15.
 */
public class FragmentUtils {

    public static final int FRAGMENT_ADD = 1;
    public static final int FRAGMENT_REPLACE = 2;
    public static final int FRAGMENT_ADD_TO_BACK_STACK = 3;

    public static void addFragment(@NonNull FragmentManager fragmentManage, @NonNull Fragment fragment, int frameId) {
        attachFragment(FRAGMENT_ADD, fragmentManage, fragment, frameId);
    }

    public static void replaceFragment(@NonNull FragmentManager fragmentManage, @NonNull Fragment fragment, int frameId) {
        attachFragment(FRAGMENT_REPLACE, fragmentManage, fragment, frameId);
    }

    public static void addToBackStackFragment(@NonNull FragmentManager fragmentManage, @NonNull Fragment fragment, int frameId) {
        attachFragment(FRAGMENT_ADD_TO_BACK_STACK, fragmentManage, fragment, frameId);
    }

    public static void attachFragment(int type, @NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId) {
        attachFragment(type,fragmentManager.beginTransaction(),fragment,frameId);
    }

    public static void attachFragment(int type, @NonNull FragmentTransaction ft, @NonNull Fragment fragment, int frameId) {
        String tag = fragment.getClass().getName();
        switch (type) {
            case FRAGMENT_ADD:
                ft.add(frameId, fragment, tag);
                break;
            case FRAGMENT_REPLACE:
                ft.replace(frameId, fragment, tag);
                break;
            case FRAGMENT_ADD_TO_BACK_STACK:
                ft.replace(frameId, fragment, tag);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                break;
            default:
                ft.replace(frameId, fragment, tag);
                break;
        }
        ft.commitAllowingStateLoss();
    }
}
