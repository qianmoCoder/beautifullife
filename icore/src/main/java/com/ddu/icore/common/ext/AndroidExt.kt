package com.ddu.icore.common.ext

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File

fun getCameraIntent(ctx: Context, file: File): Triple<Intent, File, Uri>? {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    val packageManager = ctx.packageManager
    return if (packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size > 0) {
        val photoUri = if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Uri.fromFile(file)
        } else {
            FileProvider.getUriForFile(ctx, ctx.packageName + ".provider", file)
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        Triple(intent, file, photoUri)
    } else {
        null
    }
}

fun getGallery() = Intent(
    Intent.ACTION_PICK,
    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
)

fun getAbsPath(photoUri: Uri, activity: Activity, data: Intent?): String? {
    var picturePath: String? = null
    val uri = if (data == null || data.data == null) {
        photoUri
    } else {
        data.data
    }
    val filePathColumns = arrayOf(MediaStore.Images.Media.DATA)
    if (uri == null) {
        return ""
    }
    when (uri.scheme) {
        null -> picturePath = uri.path
        ContentResolver.SCHEME_FILE -> picturePath = uri.path
        ContentResolver.SCHEME_CONTENT -> {}
    }
    val c = activity.contentResolver.query(uri, filePathColumns, null, null, null)
    if (null != c) {
        if (c.moveToFirst()) {
            picturePath = c.getString(c.getColumnIndex(filePathColumns[0]))
        }
        c.close()
    }
    return picturePath
}