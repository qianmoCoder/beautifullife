/*
 * @项目名称: 出口扫码付
 * @文件名称: ZXingUtils.java
 * @Copyright: 2016 悦畅科技有限公司. All rights reserved.
 * 注意：本内容仅限于悦畅科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.ddu.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

/**
 * Created by liuhongzhe on 16/6/2.
 */
public class ZXingUtils {

    private static final int IMAGE_HALF_WIDTH = 20;//中间图片大小

    private static int QR_WIDTH = 500;
    private static int QR_HEIGHT = 500;

    public static Bitmap createCode(Context context, String url, Bitmap mBitmap, BarcodeFormat format) throws Exception {
        Matrix m = new Matrix();
        float sx = (float) 2 * IMAGE_HALF_WIDTH / mBitmap.getWidth();
        float sy = (float) 2 * IMAGE_HALF_WIDTH / mBitmap.getHeight();

        m.setScale(sx, sy);//设置缩放信息
        //将logo图片按martrix设置的信息缩放
        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), m, false);
        MultiFormatWriter writer = new MultiFormatWriter();
        Hashtable<EncodeHintType, String> hst = new Hashtable<>();
        hst.put(EncodeHintType.CHARACTER_SET, "UTF-8"); //设置字符编码
        BitMatrix matrix = writer.encode(url, format, 200, 200, hst); //生成二维码矩阵信息
        int width = matrix.getWidth(); //矩阵高度
        int height = matrix.getHeight();//矩阵宽度
        int halfW = width / 2;
        int halfH = height / 2;
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x > halfW - IMAGE_HALF_WIDTH && x < halfW + IMAGE_HALF_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH
                        && y < halfH + IMAGE_HALF_WIDTH) {//该位置用于存放图片信息
                    //记录图片每个像素信息
                    pixels[y * width + x] = mBitmap.getPixel(x - halfW
                            + IMAGE_HALF_WIDTH, y - halfH + IMAGE_HALF_WIDTH);
                } else {
                    if (matrix.get(x, y)) {//如果有黑块点，记录信息
                        pixels[y * width + x] = 0xff000000;//记录黑块信息
                    }
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public static Bitmap createQRImage(Context context, String url) {
        try {
            if (url == null || "".equals(url) || url.length() < 1) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(url,
                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int width = bitMatrix.getWidth(); //矩阵高度
            int height = bitMatrix.getHeight();//矩阵宽度
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    }
//                    else {
//                        pixels[y * width + x] = 0xffffffff;
//                    }
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap createImage(String text, int w, int h, Bitmap logo) {
        try {
            Bitmap scaleLogo = getScaleLogo(logo, w, h);
            int offsetX = 0;
            int offsetY = 0;
            if (scaleLogo != null) {
                offsetX = (w - scaleLogo.getWidth()) / 2;
                offsetY = (h - scaleLogo.getHeight()) / 2;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(text,
                    BarcodeFormat.QR_CODE, w, h, hints);
            int[] pixels = new int[w * h];
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    //判断是否在logo图片中
                    if (offsetX != 0 && offsetY != 0 && x >= offsetX && x < offsetX + scaleLogo.getWidth() && y >= offsetY && y < offsetY + scaleLogo.getHeight()) {
                        int pixel = scaleLogo.getPixel(x - offsetX, y - offsetY);
                        //如果logo像素是透明则写入二维码信息
                        if (pixel == 0) {
                            if (bitMatrix.get(x, y)) {
                                pixel = 0xff000000;
                            } else {
                                pixel = 0xffffffff;
                            }
                        }
                        pixels[y * w + x] = pixel;

                    } else {
                        if (bitMatrix.get(x, y)) {
                            pixels[y * w + x] = 0xff000000;
                        } else {
                            pixels[y * w + x] = 0xffffffff;
                        }
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(w, h,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
            return bitmap;

        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 缩放logo到二维码的1/5
     *
     * @param logo
     * @param w
     * @param h
     * @return
     */
    private static Bitmap getScaleLogo(Bitmap logo, int w, int h) {
        if (logo == null) return null;
        Matrix matrix = new Matrix();
        float scaleFactor = Math.min(w * 1.0f / 5 / logo.getWidth(), h * 1.0f / 5 / logo.getHeight());
        matrix.postScale(scaleFactor, scaleFactor);
        Bitmap result = Bitmap.createBitmap(logo, 0, 0, logo.getWidth(), logo.getHeight(), matrix, true);
        return result;
    }
}
