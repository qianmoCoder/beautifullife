package com.ddu.icore.util.sys;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;

/**
 * Created by yzbzz on 16/7/25.
 */
public class SystemUtils {

    // 跳转系统网络设置界面
    public static boolean startActivitySettingWireless(@NonNull Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_SETTINGS);
        try {
            context.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 拨打电话
    public static final boolean startActivityTel(@NonNull Context context, String phoneNumber) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel://" + phoneNumber)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                context.startActivity(intent);
                return true;
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // 下载
    public static boolean startActivityDownload(Context context, String url) {
        return startActivitySystemBrowser(context, url);
    }

    // 使用系统浏览器打开url
    public static boolean startActivitySystemBrowser(Context context, String url) {
        try {
            if (!TextUtils.isEmpty(url)) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                if (!(context instanceof Activity)) {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                context.startActivity(intent);
                return true;
            }
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 跳到系统自带的编辑短信界面
    public static boolean startActivitySmsTo(@NonNull Context context, @Nullable String mobileNumber) {
        if (mobileNumber != null) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + mobileNumber)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                context.startActivity(intent);
                return true;
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // 跳到系统自带的编辑email的界面
    public static boolean startActivityMailTo(@NonNull Context context, @Nullable String mailAddress) {
        if (mailAddress != null) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + mailAddress));
            try {
                context.startActivity(intent);
                return true;
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // 应用是否已经安装
    public static boolean isAppInstalled(@NonNull Context context, String packageName) {
        try {
            // mContext.getPackageInfo(String packageName, int flags)第二个参数flags为0：因为不需要该程序的其他信息，只需返回程序的基本信息。
            return context.getPackageManager().getPackageInfo(packageName, 0) != null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 启动应用 如果packageName对应的应用没有找到，返回false，否则true
    public static boolean startActivityLocalApp(@NonNull Context context, String packageName, String credential) {
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            if (!TextUtils.isEmpty(credential)) {
                intent.putExtra("SSO_CREDENTIAL", credential);
            }
            context.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return false;
    }

}
