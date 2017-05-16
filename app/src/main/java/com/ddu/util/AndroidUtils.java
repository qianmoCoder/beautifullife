package com.ddu.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.ddu.R;
import com.ddu.icore.util.ToastUtils;


/**
 * Created by yzbzz on 16/4/14.
 */
public class AndroidUtils {

    public void gotoMarket(@NonNull Context context) {
        try {
            Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            ToastUtils.showErrorToast(R.string.no_install_app_store);
        }
    }

    public static void gotoIcore(@NonNull Context context) {
        try {
            Uri uri = Uri.parse("Icore://details?id=3");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
//            intent.setComponent(new ComponentName("com.ddu.myapplication", "com.ddu.myapplication.MainActivity"));
//            intent.setAction("Icore");
//            intent.setAction("Icore");
//            intent.setDataAndType(uri, context.getPackageName());
//            intent.setData(uri);
//            Intent intent = PackageMan
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            Intent intent1 = context.getPackageManager().getLaunchIntentForPackage("com.ddu.myapplication");
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            ToastUtils.showErrorToast(R.string.no_install_app_store);
        }
    }

    public void launchApp(@NonNull Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null) {
            // Activity was found, launch new app
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            // Activity not found. Send user to market
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + packageName));
            context.startActivity(intent);
        }
    }


}
