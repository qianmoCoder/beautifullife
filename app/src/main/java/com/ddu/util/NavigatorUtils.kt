package com.ddu.util

import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.app.FragmentActivity
import com.ddu.icore.common.findPreference
import com.ddu.icore.common.isNetworkConnected
import com.ddu.icore.dialog.AlertDialogFragment
import com.ddu.ui.activity.*
import org.jetbrains.anko.support.v4.ctx

/**
 * Created by yzbzz on 16/7/12.
 */
object NavigatorUtils {
    const val DEFAULT_HOST = "100"
    const val DEFAULT_URL = "url"
    const val DEFAULT_DATA = "defaultData"

    fun navigatorToNative(act: FragmentActivity, intent: Intent?): Boolean {
        var isSuccess: Boolean
        val uri = intent?.data
        val defaultData = intent?.getStringExtra(DEFAULT_DATA)
        return uri?.let {
            when (it.scheme) {
                "etcp" -> {
                    when (it.host) {
                        DEFAULT_HOST -> {
                            val tempUrl = try {
                                Uri.parse(it.getQueryParameter(DEFAULT_URL))
                            } catch (e: Exception) {
                                Uri.parse("")
                            }
                            isSuccess = if (tempUrl.toString().isNullOrEmpty()) {
                                false
                            } else {
                                navigator(act, tempUrl, defaultData)
                            }
                        }
                        else -> {
                            isSuccess = navigator(act, it, defaultData)
                        }
                    }

                }
                "http", "https" -> {
                    startActivity(act, getWebIntent(act, it.toString()))
                    isSuccess = true
                }
                else -> isSuccess = false
            }
            return isSuccess
        } ?: false

    }


    private fun navigator(act: FragmentActivity, uri: Uri?, urlString: String?): Boolean {
        var isSuccess = false
        val intent = getIntent(act, uri)
        if (null != intent) {
            isSuccess = startActivity(act, intent)
        } else {
            var isShowDialog = false
            if (urlString.isNullOrEmpty()) {
                isShowDialog = true
            } else {
                try {
                    val tempUri = Uri.parse(urlString)
                    val tempIntent = getIntent(act, tempUri)
                    if (tempIntent != null) {
                        isShowDialog = false
                        isSuccess = startActivity(act, tempIntent)
                    } else {
                        isShowDialog = true
                    }
                } catch (e: Exception) {
                    ToastUtils.showToast("不支持的defaultData格式!")
                    act.finish()
                }
            }
            if (isShowDialog) {
                val builder = AlertDialogFragment().apply {
                    msg = "您的版本太低，请更新至新版本"
                    leftText = "稍后"
                    mLeftClickListener = { _, dialogFragment ->
                        dialogFragment.dismissAllowingStateLoss()
                        act.finish()
                    }
                    rightText = "更新"
                    mRightClickListener = { _, dialogFragment ->
                        dialogFragment.dismissAllowingStateLoss()
                        if (ctx.isNetworkConnected()) {
                            ToastUtils.showToast("no_network")
                        } else {
                            ToastUtils.showToast("download")
                        }
                        act.finish()
                    }
                }
                val transaction = act.supportFragmentManager.beginTransaction()
                transaction.add(builder, "dialog")
                transaction.commitAllowingStateLoss()
            }
        }
        return isSuccess
    }

    fun isNeedLogin(uri: Uri?): Boolean {
        val login = uri?.getQueryParameter("login") ?: ""
        return login.equals("1", ignoreCase = true)
    }

    private fun startActivity(act: FragmentActivity, intent: Intent): Boolean {
        var isNavigatorSuccess = false
        val isLoad = act.findPreference("MAIN_LOADED_KEY", false) ?: false
        if (isLoad) {
            val nextIntent = Intent(act, MainActivity::class.java)
            val stackBuilder = TaskStackBuilder.create(act)
            stackBuilder.addNextIntent(nextIntent)
            stackBuilder.addNextIntent(intent)
            stackBuilder.startActivities()
            isNavigatorSuccess = true
        } else {
            act.startActivity(intent)
            isNavigatorSuccess = true
        }
        return isNavigatorSuccess
    }

    fun getIntent(ctx: Context, uri: Uri?): Intent? = uri?.run {
        when (scheme) {
            "etcp" -> when (host) {
                "0" -> Intent(ctx, LoginActivity::class.java)
                "1" -> Intent(ctx, LoginActivity1::class.java)
                "2" -> Intent(ctx, LoginActivity2::class.java)
                "3" -> Intent(ctx, MainActivity::class.java)
                "4" -> Intent(ctx, MainActivityR::class.java)
                "5" -> Intent(ctx, MainActivityT::class.java)
                "6" -> {
                    val isFeedBack = getQueryParameter("isFeedBack") ?: ""
                    val synId = getQueryParameter("synId") ?: ""
                    if (isFeedBack.equals("0", ignoreCase = true)) {
                        Intent(ctx, ScrollingActivity::class.java)
                    } else if (isFeedBack.equals("1", ignoreCase = true)) {
                        Intent(ctx, ScrollingActivity1::class.java)
                    } else {
                        Intent(ctx, ScrollingActivity3::class.java)
                    }
                }
                "8" -> Intent(ctx, TestActivity::class.java)
                "9" -> Intent(ctx, WebActivity::class.java)
                else -> null
            }
            "http", "https" -> getWebIntent(ctx, toString())
            else -> null
        }
    }

    private fun getWebIntent(ctx: Context, url: String): Intent {
        val intent = Intent(ctx, WebActivity::class.java)
        intent.putExtra("type", -1)
        intent.putExtra("url", url)
        intent.putExtra("title", "")
        return intent
    }
}
