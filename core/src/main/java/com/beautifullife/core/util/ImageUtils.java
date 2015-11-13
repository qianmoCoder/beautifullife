package com.beautifullife.core.util;

import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * Created by zhy on 15/8/11.
 */
public class ImageUtils
{
    /**
     * 鏍规嵁InputStream鑾峰彇鍥剧墖瀹為檯鐨勫搴﹀拰楂樺害
     *
     * @param imageStream
     * @return
     */
    public static ImageSize getImageSize(InputStream imageStream)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(imageStream, null, options);
        return new ImageSize(options.outWidth, options.outHeight);
    }

    public static class ImageSize
    {
        int width;
        int height;

        public ImageSize()
        {
        }

        public ImageSize(int width, int height)
        {
            this.width = width;
            this.height = height;
        }

        @Override
        public String toString()
        {
            return "ImageSize{" +
                    "width=" + width +
                    ", height=" + height +
                    '}';
        }
    }

    public static int calculateInSampleSize(ImageSize srcSize, ImageSize targetSize)
    {
        // 婧愬浘鐗囩殑瀹藉害
        int width = srcSize.width;
        int height = srcSize.height;
        int inSampleSize = 1;

        int reqWidth = targetSize.width;
        int reqHeight = targetSize.height;

        if (width > reqWidth && height > reqHeight)
        {
            // 璁＄畻鍑哄疄闄呭搴﹀拰鐩爣瀹藉害鐨勬瘮鐜�
            int widthRatio = Math.round((float) width / (float) reqWidth);
            int heightRatio = Math.round((float) height / (float) reqHeight);
            inSampleSize = Math.max(widthRatio, heightRatio);
        }
        return inSampleSize;
    }

    /**
     * 璁＄畻鍚堥�傜殑inSampleSize
     */
    public static int computeImageSampleSize(ImageSize srcSize, ImageSize targetSize, ImageView imageView)
    {
        final int srcWidth = srcSize.width;
        final int srcHeight = srcSize.height;
        final int targetWidth = targetSize.width;
        final int targetHeight = targetSize.height;

        int scale = 1;

        if (imageView == null)
        {
            scale = Math.max(srcWidth / targetWidth, srcHeight / targetHeight); // max
        } else
        {
            switch (imageView.getScaleType())
            {
                case FIT_CENTER:
                case FIT_XY:
                case FIT_START:
                case FIT_END:
                case CENTER_INSIDE:
                    scale = Math.max(srcWidth / targetWidth, srcHeight / targetHeight); // max
                    break;
                case CENTER:
                case CENTER_CROP:
                case MATRIX:
                    scale = Math.max(srcWidth / targetWidth, srcHeight / targetHeight); // min
                    break;
                default:
                    scale = Math.max(srcWidth / targetWidth, srcHeight / targetHeight); // max
                    break;
            }
        }

        if (scale < 1)
        {
            scale = 1;
        }

        return scale;
    }

    /**
     * 鏍规嵁ImageView鑾烽�傚綋鐨勫帇缂╃殑瀹藉拰楂�
     *
     * @param view
     * @return
     */
    public static ImageSize getImageViewSize(View view)
    {

        ImageSize imageSize = new ImageSize();

        imageSize.width = getExpectWidth(view);
        imageSize.height = getExpectHeight(view);

        return imageSize;
    }

    /**
     * 鏍规嵁view鑾峰緱鏈熸湜鐨勯珮搴�
     *
     * @param view
     * @return
     */
    private static int getExpectHeight(View view)
    {

        int height = 0;
        if (view == null) return 0;

        final ViewGroup.LayoutParams params = view.getLayoutParams();
        //濡傛灉鏄疻RAP_CONTENT锛屾鏃跺浘鐗囪繕娌″姞杞斤紝getWidth鏍规湰鏃犳晥
        if (params != null && params.height != ViewGroup.LayoutParams.WRAP_CONTENT)
        {
            height = view.getWidth(); // 鑾峰緱瀹為檯鐨勫搴�
        }
        if (height <= 0 && params != null)
        {
            height = params.height; // 鑾峰緱甯冨眬鏂囦欢涓殑澹版槑鐨勫搴�
        }

        if (height <= 0)
        {
            height = getImageViewFieldValue(view, "mMaxHeight");// 鑾峰緱璁剧疆鐨勬渶澶х殑瀹藉害
        }

        //濡傛灉瀹藉害杩樻槸娌℃湁鑾峰彇鍒帮紝鎲嬪ぇ鎷涳紝浣跨敤灞忓箷鐨勫搴�
        if (height <= 0)
        {
            DisplayMetrics displayMetrics = view.getContext().getResources()
                    .getDisplayMetrics();
            height = displayMetrics.heightPixels;
        }

        return height;
    }

    /**
     * 鏍规嵁view鑾峰緱鏈熸湜鐨勫搴�
     *
     * @param view
     * @return
     */
    private static int getExpectWidth(View view)
    {
        int width = 0;
        if (view == null) return 0;

        final ViewGroup.LayoutParams params = view.getLayoutParams();
        //濡傛灉鏄疻RAP_CONTENT锛屾鏃跺浘鐗囪繕娌″姞杞斤紝getWidth鏍规湰鏃犳晥
        if (params != null && params.width != ViewGroup.LayoutParams.WRAP_CONTENT)
        {
            width = view.getWidth(); // 鑾峰緱瀹為檯鐨勫搴�
        }
        if (width <= 0 && params != null)
        {
            width = params.width; // 鑾峰緱甯冨眬鏂囦欢涓殑澹版槑鐨勫搴�
        }

        if (width <= 0)

        {
            width = getImageViewFieldValue(view, "mMaxWidth");// 鑾峰緱璁剧疆鐨勬渶澶х殑瀹藉害
        }
        //濡傛灉瀹藉害杩樻槸娌℃湁鑾峰彇鍒帮紝鎲嬪ぇ鎷涳紝浣跨敤灞忓箷鐨勫搴�
        if (width <= 0)

        {
            DisplayMetrics displayMetrics = view.getContext().getResources()
                    .getDisplayMetrics();
            width = displayMetrics.widthPixels;
        }

        return width;
    }

    /**
     * 閫氳繃鍙嶅皠鑾峰彇imageview鐨勬煇涓睘鎬у��
     *
     * @param object
     * @param fieldName
     * @return
     */
    private static int getImageViewFieldValue(Object object, String fieldName)
    {
        int value = 0;
        try
        {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = field.getInt(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE)
            {
                value = fieldValue;
            }
        } catch (Exception e)
        {
        }
        return value;

    }
}
