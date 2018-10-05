package com.ddu.icore.ui.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ddu.icore.R;
import com.ddu.icore.ui.help.ShapeInject;


public class OptionItemView extends RelativeLayout {

    private Context mContext;

    private TextView tvLeftText;

    private ImageView ivLeftIcon;

    private TextView tvTitle;
    private TextView tvSubTitle;

    private TextView tvContent;

    private TextView tvRightText;
    private ImageView ivRightIcon;

    public OptionItemView(Context context) {
        this(context, null);
    }

    public OptionItemView(@NonNull Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public OptionItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        init();

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OptionItemView);

        setTextData(a, tvLeftText,
                R.styleable.OptionItemView_i_leftText,
                R.styleable.OptionItemView_i_leftTextColor,
                R.styleable.OptionItemView_i_leftTextSize);

        setImageData(a, ivLeftIcon,
                R.styleable.OptionItemView_i_leftIcon,
                R.styleable.OptionItemView_i_leftIconScaleType,
                R.styleable.OptionItemView_i_leftIconWidth,
                R.styleable.OptionItemView_i_leftIconHeight,
                R.styleable.OptionItemView_i_leftIcon_tint);

        setTextData(a, tvTitle,
                R.styleable.OptionItemView_i_title,
                R.styleable.OptionItemView_i_titleColor,
                R.styleable.OptionItemView_i_titleSize);

        setTextData(a, tvSubTitle,
                R.styleable.OptionItemView_i_subTitle,
                R.styleable.OptionItemView_i_subTitleColor,
                R.styleable.OptionItemView_i_subTitleSize);

        setTextData(a, tvContent,
                R.styleable.OptionItemView_i_content,
                R.styleable.OptionItemView_i_contentColor,
                R.styleable.OptionItemView_i_contentSize);

        setTextData(a, tvRightText,
                R.styleable.OptionItemView_i_rightText,
                R.styleable.OptionItemView_i_rightTextColor,
                R.styleable.OptionItemView_i_rightTextSize);

        setImageData(a, ivRightIcon,
                R.styleable.OptionItemView_i_rightIcon,
                R.styleable.OptionItemView_i_rightIconScaleType,
                R.styleable.OptionItemView_i_rightIconWidth,
                R.styleable.OptionItemView_i_rightIconHeight,
                R.styleable.OptionItemView_i_rightIcon_tint);

        a.recycle();
    }

    private void setImageData(TypedArray a, ImageView imageView, int resIndex, int scaleTypeIndex, int widthIndex, int heightIndex, int tintIndex) {
        int leftResId = a.getResourceId(resIndex, -1);
        if (leftResId > 0) {
            imageView.setVisibility(VISIBLE);
            imageView.setImageResource(leftResId);
        } else {
            imageView.setVisibility(GONE);
        }
        int leftImageScale = a.getInt(scaleTypeIndex, -1);
        if (leftImageScale > 0) {
            imageView.setScaleType(sScaleTypeArray[leftImageScale]);
        }

        int tintColor = a.getColor(tintIndex, -1);
        if (tintColor != -1) {
            final Drawable originBitmapDrawable = imageView.getDrawable();
            if (null != originBitmapDrawable) {
                imageView.setBackground(tintDrawable(originBitmapDrawable, ColorStateList.valueOf(tintColor)));
            }
        }

        int leftIconWidth = a.getInt(widthIndex, 0);
        int leftIconHeight = a.getInt(heightIndex, 0);
        if (leftIconWidth > 0 && leftIconHeight > 0) {
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(leftIconWidth, leftIconHeight));
        }
    }


    private void setTextData(TypedArray a, TextView textView, int textIndex, int colorIndex, int sizeIndex) {
        String title = a.getString(textIndex);
        if (!TextUtils.isEmpty(title)) {
            textView.setVisibility(VISIBLE);
            textView.setText(title);
        } else {
            textView.setVisibility(GONE);
        }

        int color = a.getColor(colorIndex, Color.BLACK);
        textView.setTextColor(color);

        int size = a.getDimensionPixelSize(sizeIndex, 16);
        textView.setTextSize(size);
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        inflater.inflate(R.layout.option_item_view, this);

        tvLeftText = findViewById(R.id.tv_left_text);
        ivLeftIcon = findViewById(R.id.iv_left_icon);
        tvTitle = findViewById(R.id.tv_title);
        tvSubTitle = findViewById(R.id.tv_sub_title);
        tvContent = findViewById(R.id.tv_content);
        tvRightText = findViewById(R.id.tv_right_text);
        ivRightIcon = findViewById(R.id.iv_right_icon);
    }

    public void enableDefaultLeftText(@ColorInt int color) {
        enableLeftText(ShapeInject.TYPE_ROUND, color, 0);
    }

    public void enableLeftText(int shapeType, @ColorInt int color) {
        enableLeftText(shapeType, color, 0);
    }

    public void enableLeftText(int shapeType, @ColorInt int color, int index) {
        CharSequence titleText = tvTitle.getText();
        if (titleText.length() > index) {
            char firstText = titleText.charAt(index);
            setLeftText(String.valueOf(firstText));
            ShapeInject.inject(tvLeftText)
                    .setShapeType(shapeType)
                    .setBackgroundColor(color)
                    .background();
        }
    }

    public void setLefText(int shapeType, @ColorInt int color) {
        CharSequence leftText = tvLeftText.getText();
        if (leftText.length() > 0) {
            ShapeInject.inject(tvLeftText)
                    .setShapeType(shapeType)
                    .setBackgroundColor(color)
                    .background();
        }
    }

//    public void setLefText(int shapeType, @ColorInt int color, CharSequence text) {
//        if (!TextUtils.isEmpty(text)) {
//            setText(tvTitle, text);
//        }
//        CharSequence titleText = tvTitle.getText();
//        if (titleText.length() > index) {
//            char firstText = titleText.charAt(index);
//            setLeftText(String.valueOf(firstText));
//            ShapeInject.inject(tvLeftText)
//                    .setShapeType(shapeType)
//                    .setBackgroundColor(color)
//                    .background();
//        }
//    }

    public void setLeftText(int resId) {
        setText(tvLeftText, resId);
    }

    public void setLeftText(CharSequence text) {
        setText(tvLeftText, text);
    }

    public void setLeftIcon(Drawable drawable) {
        setImage(ivLeftIcon, drawable);
    }

    public void setLeftIcon(int resId) {
        setImage(ivLeftIcon, resId);
    }

    public void setTitle(CharSequence text) {
        setText(tvTitle, text);
    }

    public void setTitle(int resId) {
        setText(tvTitle, resId);
    }

    public void setSubTitle(CharSequence text) {
        setText(tvSubTitle, text);
    }

    public void setSubTitle(int resId) {
        setText(tvSubTitle, resId);
    }

    public void setContent(CharSequence text) {
        setText(tvContent, text);
    }

    public void setContent(int resId) {
        setText(tvContent, resId);
    }

    public void setRightText(CharSequence text) {
        setText(tvRightText, text);
    }

    public void setRightText(int resId) {
        setText(tvRightText, resId);
    }

    public void setRightIcon(Drawable drawable) {
        setImage(ivRightIcon, drawable);
    }

    public void setRightIcon(int resId) {
        setImage(ivRightIcon, resId);
    }

    public TextView getLeftText() {
        return tvLeftText;
    }

    private void setText(TextView textView, CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(GONE);
        } else {
            textView.setVisibility(VISIBLE);
            textView.setText(text);
        }
    }

    private void setText(TextView textView, int resId) {
        if (resId < 0) {
            textView.setVisibility(GONE);
        } else {
            textView.setVisibility(VISIBLE);
            textView.setText(resId);
        }
    }

    private void setImage(ImageView imageView, Drawable drawable) {
        if (null == drawable) {
            imageView.setVisibility(GONE);
        } else {
            imageView.setVisibility(VISIBLE);
            imageView.setImageDrawable(drawable);
        }
    }

    private void setImage(ImageView imageView, int resId) {
        if (resId < 0) {
            imageView.setVisibility(GONE);
        } else {
            imageView.setVisibility(VISIBLE);
            imageView.setImageResource(resId);
        }
    }

    private static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }


    private static final ImageView.ScaleType[] sScaleTypeArray = {
            ImageView.ScaleType.MATRIX,
            ImageView.ScaleType.FIT_XY,
            ImageView.ScaleType.FIT_START,
            ImageView.ScaleType.FIT_CENTER,
            ImageView.ScaleType.FIT_END,
            ImageView.ScaleType.CENTER,
            ImageView.ScaleType.CENTER_CROP,
            ImageView.ScaleType.CENTER_INSIDE
    };
}
