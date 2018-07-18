package com.ddu.icore.rx.activityresult

import android.app.Activity
import android.content.Intent

/**
 * Created by yzbzz on 2018/1/5.
 */

data class ActivityResultInfo(var requestCode: Int = 0, var resultCode: Int = 0, var data: Intent? = null) {

    val isOk = (resultCode == Activity.RESULT_OK)

    val isCancel = (resultCode == Activity.RESULT_CANCELED)
}
