package com.beautifullife.core.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by admin on 2015/10/8.
 */
public class IntentHelper {

    public static void sendMessage(Context context, String phoneNumber, String content) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        intent.setData(uri);
        intent.putExtra("sms_body", content);
        startActivity(context, intent);
    }

    /**
     * @param addresses
     * @param copy
     * @param bcc
     */
    public static void sendMail(Context context, String[] addresses, String[] copy, String[] bcc, String subject, String content) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        Uri uri = Uri.parse("mailto:");
        intent.setData(uri);
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_CC, copy);
        intent.putExtra(Intent.EXTRA_BCC, bcc);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);
//        intent.putExtra(Intent.EXTRA_STREAM,Uri.parse("..."));
        startActivity(context, intent);
    }

    public static void call(Context context, String phoneNumber) {
        call(context, new Intent(Intent.ACTION_CALL), phoneNumber);
    }

    public static void dial(Context context, String phoneNumber) {
        call(context, new Intent(Intent.ACTION_DIAL), phoneNumber);
    }

    private static void call(Context context, Intent intent, String phoneNumber) {
        Uri uri = Uri.parse("tel:" + phoneNumber);
        intent.setData(uri);
        startActivity(context, intent);
    }

    public static void captureImage(Activity context, File file,int requestCode){
        capture(context, new Intent(MediaStore.ACTION_IMAGE_CAPTURE), file, requestCode);
    }

    public static void captureVideo(Activity context, File file,int requestCode){
        capture(context,new Intent(MediaStore.ACTION_VIDEO_CAPTURE),file,requestCode);
    }


    private static void capture(Activity context, Intent intent, File file,int requestCode) {
        Uri uri = null;
        PackageManager pm = context.getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            ComponentName componentName = intent.resolveActivity(context.getPackageManager());
            if (componentName != null) {
                if (file != null) {
                    uri = Uri.fromFile(file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                    context.startActivityForResult(intent,requestCode);
                }
            }
        }
    }

    private static void startActivity(Context context, Intent intent) {
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        if (componentName != null) {
            context.startActivity(intent);
        }
    }

}
