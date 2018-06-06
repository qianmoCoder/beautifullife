package com.ddu.ui.fragment.study.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.icore.util.ToastUtils
import com.iannotation.Element
import kotlinx.android.synthetic.main.fragment_permission.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.ctx
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest


/**
 * Created by yzbzz on 2017/11/14.
 */
@Element("UI")
class PermissionFragment : DefaultFragment(), View.OnClickListener, EasyPermissions.PermissionCallbacks {


    override fun getLayoutId(): Int {
        return R.layout.fragment_permission
    }

    override fun initView() {
        btn_easy.setOnClickListener(this)
        btn_android.setOnClickListener(this)
        setDefaultTitle("拍照")
    }

    override fun initData(savedInstanceState: Bundle?) {

    }


    // Manifest.permission.WRITE_EXTERNAL_STORAGE
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_easy -> {
                easyRequestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            R.id.btn_android -> {
            }
        }
    }

    fun easyRequestPermission(permission: String) {
        if (EasyPermissions.hasPermissions(ctx, permission)) {
            ToastUtils.showToast("easyRequestPermission获得权限")
        } else {
            val permissionRequest = PermissionRequest.Builder(this, MY_PERMISSIONS_REQUEST_READ_CONTACTS, permission)
                    .setRationale("APP需要开启存储权限").build()
            EasyPermissions.requestPermissions(permissionRequest)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    fun checkPermission(permission: String) {
        val permissionCheck = ContextCompat.checkSelfPermission(ctx, permission)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(act, permission)) {
            } else {
                ActivityCompat.requestPermissions(act, arrayOf(permission), MY_PERMISSIONS_REQUEST_READ_CONTACTS)
            }
        } else {

        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).setTitle("请开启权限").setRationale("开启权限才能使用哦").build().show()
        } else {
            ToastUtils.showToast("已拒绝权限")
//            EasyPermissions.hasPermissions(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            if (EasyPermissions.hasPermissions(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ToastUtils.showToast("获得权限")
            } else {
                ToastUtils.showToast("请到设置去开启")
            }
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        ToastUtils.showToast("已请求权限")
    }

    companion object {
        var MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0x001
    }

}
