//package com.ddu.icore.ui.view;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.util.AttributeSet;
//import android.view.View;
//
//import com.ddu.icore.R;
//
///**
// * Created by yzbzz on 2016/11/1.
// */
//
//public class CustomItemView extends View {
//
//    private Bitmap mImage;
//    private int mImageScale;
//
//    private String mTextTitle;
//    private int mTextTitleColor;
//    private int mTextTitleSize;
//
//    private String mDescriptionTextTitle;
//    private int mDescriptionTextTitleColor;
//    private int mDescriptionTextTitleSize;
//
//    private Bitmap mArrowImage;
//    private int mArrowImageScale;
//
//    private Rect mRect;
//    private Paint mPaint;
//
//    public CustomItemView(Context context) {
//        super(context);
//        init();
//    }
//
//    public CustomItemView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomItemView);
//
//        int resId = a.getResourceId(R.styleable.CustomItemView_image, -1);
//        if (resId > 0) {
//            mImage = BitmapFactory.decodeResource(getResources(), resId);
//        }
//        mImageScale = a.getInt(R.styleable.CustomItemView_imageScaleType, -1);
//
//        mTextTitle = a.getString(R.styleable.CustomItemView_titleText);
//        mTextTitleColor = a.getColor(R.styleable.CustomItemView_titleTextColor, Color.BLACK);
//        mTextTitleSize = a.getDimensionPixelSize(R.styleable.CustomItemView_titleTextColor, 16);
//
//        mDescriptionTextTitle = a.getString(R.styleable.CustomItemView_descriptionText);
//        mDescriptionTextTitleColor = a.getColor(R.styleable.CustomItemView_descriptionTextColor, 0x666666);
//        mDescriptionTextTitleSize = a.getDimensionPixelSize(R.styleable.CustomItemView_descriptionTextSize, 14);
//
//        int arrowResId = a.getResourceId(R.styleable.CustomItemView_arrowImage, -1);
//        if (arrowResId > 0) {
//            mArrowImage = BitmapFactory.decodeResource(getResources(), arrowResId);
//        }
//        mArrowImageScale = a.getInt(R.styleable.CustomItemView_arrowImageScaleType, -1);
//        a.recycle();
//        init();
//    }
//
//    public void init() {
//        mRect = new Rect();
//        mPaint = new Paint();
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//    }
//}
