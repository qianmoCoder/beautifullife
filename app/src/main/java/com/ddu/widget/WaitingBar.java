package com.ddu.widget;

/*
 * @项目名称: ETCP停车
 * @文件名称: WaitingBar.java
 * @Copyright: 2016 悦畅科技有限公司. All rights reserved.
 * 注意：本内容仅限于悦畅科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ddu.R;


@SuppressLint("NewApi")
public class WaitingBar extends LinearLayout {
    private static final int NUM = 3;
    private Context context;

    public WaitingBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init(attrs);
    }

    public WaitingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public WaitingBar(Context context) {
        super(context);
        this.context = context;
        init(null);
    }

    private void init(AttributeSet attrs) {
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setGravity(Gravity.CENTER);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.office_waitingbar_indicator_sel);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.WaitingBarStyleable);
        int width = a.getDimensionPixelSize(R.styleable.WaitingBarStyleable_point_width, bitmap.getWidth());
        int height = a.getDimensionPixelSize(R.styleable.WaitingBarStyleable_point_height, bitmap.getHeight());
        a.recycle();

        LinearLayout.LayoutParams tLayoutParams = new LinearLayout.LayoutParams(width, height);
        tLayoutParams.leftMargin = 15;
        tLayoutParams.rightMargin = 15;
        for (int i = 0; i < NUM; i++) {
            ImageView vDot = new ImageView(context);
            vDot.setLayoutParams(tLayoutParams);
            vDot.setVisibility(View.INVISIBLE);
            vDot.setBackgroundResource(R.drawable.office_waitingbar_indicator_sel);
            this.addView(vDot);
        }
        new UpdateHandler().sendEmptyMessage(0);

    }

    class UpdateHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int tPosition = msg.what;

            for (int index = 0; index < NUM; index++) {
                ImageView currentDot = (ImageView) WaitingBar.this
                        .getChildAt(index);
                currentDot.setVisibility(VISIBLE);
                if (tPosition == NUM) {
                    currentDot.setVisibility(INVISIBLE);
                } else {
                    currentDot.setVisibility((index <= tPosition) ? VISIBLE : INVISIBLE);
                }
            }

            if (tPosition == NUM) {
                tPosition = 0;
            } else {
                tPosition++;
            }
            this.sendEmptyMessageDelayed(tPosition, 300);
        }
    }
}

