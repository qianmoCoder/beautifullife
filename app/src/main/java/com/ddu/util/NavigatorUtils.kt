package com.ddu.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.app.FragmentActivity
import com.ddu.icore.util.ToastUtils
import com.ddu.ui.activity.*

/**
 * Created by yzbzz on 16/7/12.
 */
object NavigatorUtils {

    const val DEFAULT_HOST = "100"
    const val DEFAULT_URL = "url"
    const val DEFAULT_DATA = "defaultData"

    fun navigatorToNative(act: FragmentActivity, intent: Intent?): Boolean {
        var isSuccess = false
        val uri = intent?.data
        return uri?.let {
            when (it.scheme) {
                "etcp" -> {
                    val defaultData = intent.getStringExtra(DEFAULT_DATA)
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
                                val isNeedLogin = isNeedLogin(uri)
                                navigatorTO(act, isNeedLogin, tempUrl, defaultData)
                            }
                        }
                        else -> {
                            val isNeedLogin = isNeedLogin(uri)
                            isSuccess = navigatorTO(act, isNeedLogin, uri, defaultData)
                        }
                    }

                }
                "http", "https" -> {
                    ToastUtils.showToast("跳转http成功: $it")
                    isSuccess = true
                }
                else -> isSuccess = false
            }
            return isSuccess
        } ?: false
    }

    private fun navigatorTO(act: FragmentActivity, isNeedLogin: Boolean, uri: Uri?, urlString: String?): Boolean {
        var isSuccess = false
        val intent = getIntentByUri(act, uri)
        if (null != intent) {
            isSuccess = doOnNext(act, isNeedLogin, intent)
        } else {
            var isShowDialog = false
            if (urlString.isNullOrEmpty()) {
                isShowDialog = true
            } else {
                try {
                    val tempUri = Uri.parse(urlString)
                    val tempIntent = NavigatorUtils.getIntentByUri(act, tempUri)
                    if (tempIntent != null) {
                        isShowDialog = false
                        isSuccess = doOnNext(act, isNeedLogin, tempIntent)
                    } else {
                        isShowDialog = true
                    }
                } catch (e: Exception) {
                    ToastUtils.showToast("不支持的defaultData格式!")
                }
            }
            if (isShowDialog) {
                ToastUtils.showToast("您的版本太低，请更新至新版本")
            }
        }
        return isSuccess
    }

    private fun isNeedLogin(uri: Uri?): Boolean {
        val login = uri?.getQueryParameter("login") ?: ""
        return login.equals("1", ignoreCase = true)
    }


    private fun doOnNext(act: FragmentActivity, isNeedLogin: Boolean, intent: Intent): Boolean {
        var isNavigatorSuccess = false
        if (isNeedLogin) {
            ToastUtils.showToast("登录成功，跳转成功 ${intent.component.className}")
        } else {
            isNavigatorSuccess = true

            ToastUtils.showToast("跳转成功 ${intent.component.className}")
        }
        return isNavigatorSuccess
    }


    fun getIntentByUri(ctx: Context, uri: Uri?): Intent? = uri?.run {
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
                "7" -> Intent(ctx, ShareActivity::class.java)
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
