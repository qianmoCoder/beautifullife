/*
 * @项目名称: 出口扫码付
 * @文件名称: ZXingUtils.java
 * @Copyright: 2016 悦畅科技有限公司. All rights reserved.
 * 注意：本内容仅限于悦畅科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.ddu.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.support.annotation.IntRange
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.util.*

/**
 * Created by liuhongzhe on 16/6/2.
 */
object ZXingUtils {

    private const val WHITE = -0x1
    private const val BLACK = -0x1000000

    private const val QR_WIDTH = 500
    private const val QR_HEIGHT = 500

    private const val IMAGE_HALF_WIDTH = 20//中间图片大小

    fun createBitmap(matrix: BitMatrix): Bitmap {
        val width = matrix.width
        val height = matrix.height
        val pixels = IntArray(width * height)
        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixels[offset + x] = if (matrix.get(x, y)) BLACK else WHITE
            }
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }

    fun encodeBitmap(str: String, logoBitmap: Bitmap?): Bitmap? {
        return try {
            val hints = Hashtable<EncodeHintType, Any>()
            hints[EncodeHintType.CHARACTER_SET] = "UTF-8" //字符集
            hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H //容错率
            hints[EncodeHintType.MARGIN] = 0 //白边宽度
            val bitmap = encodeBitmap(str, hints = hints)
            if (bitmap != null && logoBitmap != null) {
                encodeLogoBitmap(bitmap, logoBitmap)
            } else {
                bitmap
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun encodeBitmap(str: String, width: Int = QR_WIDTH, height: Int = QR_HEIGHT, hints: Map<EncodeHintType, *>? = null): Bitmap? {
        return try {
            createBitmap(QRCodeWriter().encode(str, BarcodeFormat.QR_CODE, width, height, hints))
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun encodeLogoBitmap(srcBitmap: Bitmap, logoBitmap: Bitmap, @IntRange(from = 0, to = 1) percent: Float = 0.2f): Bitmap {
        val srcWidth = srcBitmap.width
        val srcHeight = srcBitmap.height

        val logoWidth = logoBitmap.width
        val logoHeight = logoBitmap.height

        val scaleWidth = srcWidth * percent / logoWidth
        val scaleHeight = srcHeight * percent / logoHeight

        val bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)
        canvas.drawBitmap(srcBitmap, 0f, 0f, null)
        canvas.scale(scaleWidth, scaleHeight, srcWidth / 2f, srcHeight / 2f)
        canvas.drawBitmap(logoBitmap, (srcWidth - logoWidth) / 2f, (srcHeight - logoHeight) / 2f, null)

        canvas.save()
        canvas.restore()
        if (bitmap.isRecycled) {
            bitmap.recycle()
        }
        return bitmap
    }


    @Throws(Exception::class)
    fun createCode(context: Context, url: String, mBitmap: Bitmap, format: BarcodeFormat): Bitmap {
        var mBitmap = mBitmap

        val m = Matrix()
        val sx = 2.toFloat() * IMAGE_HALF_WIDTH / mBitmap.width
        val sy = 2.toFloat() * IMAGE_HALF_WIDTH / mBitmap.height

        m.setScale(sx, sy)//设置缩放信息
        //将logo图片按martrix设置的信息缩放
        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.width, mBitmap.height, m, false)
        val writer = MultiFormatWriter()
        val hst = Hashtable<EncodeHintType, String>()
        hst[EncodeHintType.CHARACTER_SET] = "UTF-8" //设置字符编码

        val matrix = writer.encode(url, format, 200, 200, hst) //生成二维码矩阵信息
        val width = matrix.width //矩阵高度
        val height = matrix.height//矩阵宽度

        val halfW = width / 2
        val halfH = height / 2
        val pixels = IntArray(width * height)
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (x > halfW - IMAGE_HALF_WIDTH && x < halfW + IMAGE_HALF_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH
                        && y < halfH + IMAGE_HALF_WIDTH) {//该位置用于存放图片信息
                    //记录图片每个像素信息
                    pixels[y * width + x] = mBitmap.getPixel(x - halfW + IMAGE_HALF_WIDTH, y - halfH + IMAGE_HALF_WIDTH)
                } else {
                    pixels[y * width + x] = if (matrix.get(x, y)) BLACK else WHITE
                }
            }
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        // 通过像素数组生成bitmap
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }


    fun createImage(text: String, w: Int = QR_WIDTH, h: Int = QR_HEIGHT, logo: Bitmap): Bitmap? {
        try {
            val scaleLogo = getScaleLogo(logo, w, h)
            var offsetX = 0
            var offsetY = 0
            if (scaleLogo != null) {
                offsetX = (w - scaleLogo.width) / 2
                offsetY = (h - scaleLogo.height) / 2
            }
            val hints = Hashtable<EncodeHintType, String>()
            hints[EncodeHintType.CHARACTER_SET] = "utf-8"
            val bitMatrix = QRCodeWriter().encode(text,
                    BarcodeFormat.QR_CODE, w, h, hints)
            val pixels = IntArray(w * h)
            for (y in 0 until h) {
                for (x in 0 until w) {
                    //判断是否在logo图片中
                    if (offsetX != 0 && offsetY != 0 && x >= offsetX && x < offsetX + scaleLogo!!.width && y >= offsetY && y < offsetY + scaleLogo.height) {
                        var pixel = scaleLogo.getPixel(x - offsetX, y - offsetY)
                        //如果logo像素是透明则写入二维码信息
                        if (pixel == 0) {
                            if (bitMatrix.get(x, y)) {
                                pixel = -0x1000000
                            } else {
                                pixel = -0x1
                            }
                        }
                        pixels[y * w + x] = pixel

                    } else {
                        if (bitMatrix.get(x, y)) {
                            pixels[y * w + x] = -0x1000000
                        } else {
                            pixels[y * w + x] = -0x1
                        }
                    }
                }
            }
            val bitmap = Bitmap.createBitmap(w, h,
                    Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, w, 0, 0, w, h)
            return bitmap

        } catch (e: WriterException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 缩放logo到二维码的1/5
     *
     * @param logo
     * @param w
     * @param h
     * @return
     */
    private fun getScaleLogo(logo: Bitmap?, w: Int, h: Int): Bitmap? {
        if (logo == null) return null
        val matrix = Matrix()
        val scaleFactor = Math.min(w * 1.0f / 5f / logo.width.toFloat(), h * 1.0f / 5f / logo.height.toFloat())
        matrix.postScale(scaleFactor, scaleFactor)
        return Bitmap.createBitmap(logo, 0, 0, logo.width, logo.height, matrix, true)
    }
}
