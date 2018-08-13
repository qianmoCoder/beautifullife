package com.ddu.icore.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ddu.icore.R;


public class OptionItemView extends RelativeLayout {

    private Context mContext;

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

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IOptionItemViewStyle);

        setImageData(a, ivLeftIcon,
                R.styleable.IOptionItemViewStyle_i_leftIcon,
                R.styleable.IOptionItemViewStyle_i_leftIconScaleType,
                R.styleable.IOptionItemViewStyle_i_leftIconWidth,
                R.styleable.IOptionItemViewStyle_i_leftIconHeight);

        setTextData(a, tvTitle,
                R.styleable.IOptionItemViewStyle_i_title,
                R.styleable.IOptionItemViewStyle_i_titleColor,
                R.styleable.IOptionItemViewStyle_i_titleSize);

        setTextData(a, tvSubTitle,
                R.styleable.IOptionItemViewStyle_i_subTitle,
                R.styleable.IOptionItemViewStyle_i_subTitleColor,
                R.styleable.IOptionItemViewStyle_i_subTitleSize);

        setTextData(a, tvContent,
                R.styleable.IOptionItemViewStyle_i_content,
                R.styleable.IOptionItemViewStyle_i_contentColor,
                R.styleable.IOptionItemViewStyle_i_contentSize);

        setTextData(a, tvRightText,
                R.styleable.IOptionItemViewStyle_i_rightText,
                R.styleable.IOptionItemViewStyle_i_rightTextColor,
                R.styleable.IOptionItemViewStyle_i_rightTextSize);

        setImageData(a, ivRightIcon,
                R.styleable.IOptionItemViewStyle_i_rightIcon,
                R.styleable.IOptionItemViewStyle_i_rightIconScaleType,
                R.styleable.IOptionItemViewStyle_i_rightIconWidth,
                R.styleable.IOptionItemViewStyle_i_rightIconHeight);

        a.recycle();
    }

    private void setImageData(TypedArray a, ImageView imageView, int resIndex, int scaleTypeIndex, int widthIndex, int heightIndex) {
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
        ivLeftIcon = findViewById(R.id.iv_left_icon);
        tvTitle = findViewById(R.id.tv_title);
        tvSubTitle = findViewById(R.id.tv_sub_title);
        tvContent = findViewById(R.id.tv_content);
        tvRightText = findViewById(R.id.tv_right_text);
        ivRightIcon = findViewById(R.id.iv_right_icon);
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
