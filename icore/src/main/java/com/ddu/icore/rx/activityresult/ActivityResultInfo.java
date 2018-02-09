package com.ddu.icore.rx.activityresult;

import android.content.Intent;

/**
 * Created by yzbzz on 2018/1/5.
 */

public class ActivityResultInfo {

    private int requestCode;
    private int resultCode;
    private Intent data;

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public Intent getData() {
        return data;
    }

    public void setData(Intent data) {
        this.data = data;
    }
}
