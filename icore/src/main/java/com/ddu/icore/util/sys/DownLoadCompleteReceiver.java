/*
 * @项目名称: 出口扫码付
 * @文件名称: DownLoadCompleteReceiver.java
 * @Copyright: 2016 悦畅科技有限公司. All rights reserved.
 * 注意：本内容仅限于悦畅科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.ddu.icore.util.sys;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.ddu.icore.aidl.GodIntent;
import com.ddu.icore.common.ObserverManager;
import com.ddu.icore.logic.Actions;
import com.ddu.icore.util.ToastUtils;


/**
 * Created by yzbzz on 16/7/12.
 */
public class DownLoadCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            long downLoadId = DownloadManagerUtils.DOWNLOAD_ID;
            if (id == downLoadId) {
                DownloadManagerUtils.DOWNLOAD_ID = -1;
                Cursor cursor = DownloadManagerUtils.query(context, id);
                if (cursor.moveToFirst()) {
                    int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    switch (status) {
                        //下载暂停
                        case DownloadManager.STATUS_PAUSED:
                            break;
                        //下载延迟
                        case DownloadManager.STATUS_PENDING:
                            break;
                        //正在下载
                        case DownloadManager.STATUS_RUNNING:
                            break;
                        //下载完成
                        case DownloadManager.STATUS_SUCCESSFUL:
                            GodIntent godIntent = new GodIntent();
                            godIntent.setAction(Actions.DOWNLOAD_COMPLETE);
                            godIntent.putLong("downloadId", id);
                            ObserverManager.getInstance().notify(godIntent);
//                            DownloadManagerUtils.startInstall(context.getApplicationContext(), id);
                            break;
                        //下载失败
                        case DownloadManager.STATUS_FAILED:
                            ToastUtils.showToast("下载失败");
                            break;
                    }
                }
                cursor.close();
            }

        }
    }
}
