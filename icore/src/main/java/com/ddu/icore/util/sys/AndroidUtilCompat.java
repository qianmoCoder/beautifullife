package com.ddu.icore.util.sys;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public class AndroidUtilCompat {
    private static final String TAG = "AndroidUtilCompat";

    /**
     * 获取短信信息，注意：为解决双卡双待手机解析短信异常问题，使用Java反射机制，优先解析GSM类型的短信，假如解析失败才按CDMA类型的短信进行解析）
     *
     * @param intent
     * @return
     */
    @Nullable
    public static SmsMessage[] getSmsMessage(@NonNull Intent intent) {
        SmsMessage[] msgs = null;
        Object messages[] = (Object[]) intent.getSerializableExtra("pdus");
        int len = 0;
        if (null != messages && (len = messages.length) > 0) {
            msgs = new SmsMessage[len];
            try {
                for (int i = 0; i < len; i++) {
                    SmsMessage message = null;
                    if ("GSM".equals(intent.getStringExtra("from"))) { // 适配MOTO XT800双卡双待手机
                        message = createFromPduGsm((byte[]) messages[i]);
                    } else if ("CDMA".equals(intent.getStringExtra("from"))) { // 适配MOTO XT800双卡双待手机
                        message = createFromPduCdma((byte[]) messages[i]);
                    } else {
                        message = SmsMessage.createFromPdu((byte[]) messages[i]); // 系统默认的解析短信方式
                    }
                    if (null == message) { // 解决双卡双待类型手机解析短信异常问题
                        message = createFromPduGsm((byte[]) messages[i]);
                        if (null == message) {
                            message = createFromPduCdma((byte[]) messages[i]);
                        }
                    }
                    if (null != message) {
                        msgs[i] = message;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                msgs = getSmsMessageByReflect(intent); // 解决双卡双待手机解析短信异常问题
            } catch (Error er) {
                er.printStackTrace();
                msgs = getSmsMessageByReflect(intent); // 解决双卡双待手机解析短信异常问题
            }
        }
        return msgs;
    }

    /**
     * 使用Java反射机制获取短信信息（解决双卡双待手机解析短信异常问题，优先解析GSM类型的短信，假如解析失败才按CDMA类型的短信进行解析）
     *
     * @param intent
     * @return
     */
    @Nullable
    private static SmsMessage[] getSmsMessageByReflect(@NonNull Intent intent) {
        SmsMessage[] msgs = null;
        Object messages[] = (Object[]) intent.getSerializableExtra("pdus");
        int len = 0;
        if (null != messages && (len = messages.length) > 0) {
            msgs = new SmsMessage[len];
            try {
                for (int i = 0; i < len; i++) {
                    SmsMessage message = createFromPduGsm((byte[]) messages[i]);
                    if (null == message) {
                        message = createFromPduCdma((byte[]) messages[i]);
                    }
                    if (null != message) {
                        msgs[i] = message;
                    }
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return msgs;
    }

    /**
     * 通过Java反射机制解析GSM类型的短信
     *
     * @param pdu
     * @return
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    @Nullable
    private static SmsMessage createFromPduGsm(byte[] pdu) throws SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        return createFromPdu(pdu, "com.android.internal.telephony.gsm.SmsMessage");
    }

    /**
     * 通过Java反射机制解析CDMA类型的短信
     *
     * @param pdu
     * @return
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    @Nullable
    private static SmsMessage createFromPduCdma(byte[] pdu) throws SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        return createFromPdu(pdu, "com.android.internal.telephony.cdma.SmsMessage");
    }

    /**
     * 通过Java反射机制解析GSM或者CDMA类型的短信
     *
     * @param pdu
     * @param className GSM: com.android.internal.telephony.gsm.SmsMessage, CDMA: com.android.internal.telephony.cdma.SmsMessage
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws Exception
     */
    private static SmsMessage createFromPdu(byte[] pdu, String className) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        Class<?> clazz = Class.forName(className);
        Object object = clazz.getMethod("createFromPdu", byte[].class).invoke(clazz.newInstance(), pdu);
        if (null != object) {
            Constructor<?> constructor = SmsMessage.class.getDeclaredConstructor(Class.forName("com.android.internal.telephony.SmsMessageBase"));
            constructor.setAccessible(true);
            return (SmsMessage) constructor.newInstance(object);
        } else {
            return null;
        }
    }

    /**
     * 快捷方式是否已经存在<b><font color="red">（需要适配）</font></b><br />
     * 创建快捷方式时，"duplicate" = false 不起作用时，使用该方法做弥补 <uses-permission android:name="com.android.launcher.permission.READ_SETTINGS" />
     *
     * @param context
     * @param title
     * @param intent
     * @return
     */
    protected static boolean shortcutExists(@NonNull Context context, String title, @NonNull Intent intent) {
        boolean shortcutExists = false;
        Intent queryIntent = new Intent();
        queryIntent.setAction(Intent.ACTION_MAIN);
        queryIntent.addCategory(Intent.CATEGORY_HOME);
        List<IntentFilter> outFilters = null;
        List<ComponentName> outActivities = null;
        Cursor cursor = null;
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(queryIntent, PackageManager.MATCH_DEFAULT_ONLY);
        try {
            if (resolveInfos != null && resolveInfos.size() > 0) {
                outFilters = new ArrayList<IntentFilter>(); // Intent list cannot be null. so pass empty list
                outActivities = new ArrayList<ComponentName>();
                for (int i = resolveInfos.size() - 1; i >= 0; i--) {
                    String packageName = resolveInfos.get(i).activityInfo.packageName;
                    packageManager.getPreferredActivities(outFilters, outActivities, packageName); // 查询已经设置该应用作为默认打开方式的Activities
                    if (resolveInfos.size() == 1 // 系统只有一个主桌面
                            || AndroidUtils.isAppProcessesRuning(context, packageName) // 当前正在运行的主桌面
                            || outActivities.size() > 0 // 是系统当前默认的主桌面程序
                            ) {
                        if ("com.sec.android.app.twlauncher".equals(packageName)) {
                            // 三星手机的TouchWiz30Launcher应用程序，包名：com.sec.android.app.twlauncher
                            // TODO 暂时无法解决：在飞信程序创建快捷方式前，手工拖动应用程序图标到桌面创建快捷方式的情况下无法查询
                            shortcutExists = getShortcutCount(context, title, "content://com.sec.android.app.twlauncher.settings/favorites?notify=true", intent.toUri(0)) > 0;
                        } else if ("com.htc.launcher".equals(packageName)) {
                            // HTC手机的Rosie应用程序，包名：com.htc.launcher
                            // 注意：需要在AndroidManifest.xml中添加权限 <uses-permission android:name="com.htc.launcher.permission.READ_SETTINGS" />
                            shortcutExists = getShortcutCount(context, title, "content://com.htc.launcher.settings/favorites?notify=true", intent.toUri(0)) > 0;
                            if (!shortcutExists) { // HTC手机特殊处理，快捷方式图标和应用程序列表图标的Intent是一样的
                                Intent intentMark = new Intent(intent);
                                intentMark.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                                shortcutExists = getShortcutCount(context, title, "content://com.htc.launcher.settings/favorites?notify=true", intentMark.toUri(0)) > 1;
                            }
                        } else if ("com.miui.home".equals(packageName)
                                || (("Xiaomi".equalsIgnoreCase(Build.MANUFACTURER) || "MIUI".equalsIgnoreCase(Build.ID)) && "com.android.launcher".equals(packageName))) {
                            // 小米手机的MiuiHome应用程序，包名：com.miui.home
                            // 有的批次手机主界面的包是com.android.launcher而非com.miui.home
                            // 注意：小米手机的Launcher没有分应用程序列表界面和widget界面，只有一个界面
                            shortcutExists = true;
                        } else if ("com.motorola.blur.home".equals(packageName)) {
                            // 摩托罗拉手机的BlurHome应用程序，包名：com.motorola.blur.home
                            shortcutExists = getShortcutCount(context, title, "content://com.android.launcher.settings/favorites?notify=true", intent.toUri(0)) > 0;
                            if (!shortcutExists) { // 摩托罗拉手机特殊处理，在飞信程序创建快捷方式前，手工拖动应用程序图标到桌面创建快捷方式的情况
                                Intent intentMark = new Intent(intent);
                                intentMark.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                                shortcutExists = getShortcutCount(context, title, "content://com.android.launcher.settings/favorites?notify=true", intentMark.toUri(0)) > 0;
                            }
                        }
                        outFilters.clear();
                        outActivities.clear();
                        if (shortcutExists) {
                            break;
                        }
                    }
                }
            }
        } finally {
            if (resolveInfos != null) {
                resolveInfos.clear();
                resolveInfos = null;
            }
            if (outFilters != null) {
                outFilters.clear();
                outFilters = null;
            }
            if (outActivities != null) {
                outActivities.clear();
                outActivities = null;
            }
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
        return shortcutExists;
    }

    /**
     * 查询桌面快捷方式的个数
     *
     * @param context
     * @param selectionTitle
     * @param queryUriString
     * @param selectionIntentUriString
     * @return
     */
    private static int getShortcutCount(@NonNull Context context, String selectionTitle, String queryUriString, String selectionIntentUriString) {
        int shortcutCount = 0;
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(Uri.parse(queryUriString), new String[]{"title", "intent"}, "title=? and intent=?",
                    new String[]{selectionTitle, selectionIntentUriString}, null);
            if (null != cursor && 0 < cursor.getCount()) {
                shortcutCount = cursor.getCount();
            }

            // // 测试：查询所有桌面快捷方式
            if (null != cursor) {
                cursor.close();
                cursor = null;
            }
            cursor = context.getContentResolver().query(Uri.parse(queryUriString), null, null, null, null);
            if (null != cursor && cursor.moveToFirst()) {
                while (cursor.moveToNext()) {
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
        return shortcutCount;
    }
}
