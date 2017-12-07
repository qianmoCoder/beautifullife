package com.ddu.icore.util.sys;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.ddu.icore.util.ToastUtils;

import java.io.File;

/**
 * Created by yzbzz on 2017/6/19.
 */

public class DownloadManagerUtils {

    public static long DOWNLOAD_ID = -1;

    public static final String DOWNLOAD_DIR_TYPE = Environment.DIRECTORY_DOWNLOADS;
    public static final File DOWNLOAD_DIR = Environment.getExternalStoragePublicDirectory(DOWNLOAD_DIR_TYPE);

    public static void downLoad(Context context, String apkName, String url) {
        if (DOWNLOAD_ID != -1) {
            ToastUtils.showTextToast("应用正在下载,请稍候");
        } else {
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            long id = downloadManager.enqueue(getRequest(apkName, url));
            DOWNLOAD_ID = id;
        }
    }

    private static DownloadManager.Request getRequest(String apkName, String url) {
        Uri Download_Uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle("Download");
        request.setDescription("Android Data download");
        request.setDestinationInExternalPublicDir(DOWNLOAD_DIR_TYPE, apkName);

        request.allowScanningByMediaScanner();
        request.setMimeType("application/vnd.android.package-archive");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        return request;
    }

    public static Cursor query(Context context, long id) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(id);
        Cursor cursor = downloadManager.query(query);
        return cursor;
    }

    public static void remove(Context context, long id) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.remove(id);
    }

    public static void startInstall(Context context, long id) {
        try {
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = downloadManager.getUriForDownloadedFile(id);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void startInstall(Context context, String apkName) {
        try {
            File apkFile = new File(DOWNLOAD_DIR, apkName);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", apkFile);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                uri = Uri.fromFile(apkFile);
            }
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
