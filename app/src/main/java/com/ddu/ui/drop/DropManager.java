package com.ddu.ui.drop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextPaint;

import com.ddu.R;
import com.ddu.icore.util.sys.ScreenUtil;


public class DropManager {

    // constant
    private static final String TAG = "DropManager";

    static final int TEXT_SIZE = ScreenUtil.sp2px(12); // 12sp

    static final int CIRCLE_RADIUS = ScreenUtil.dip2px(10); // 10dip

    // single instance
    private static DropManager instance;

    public static synchronized DropManager getInstance() {
        if (instance == null) {
            instance = new DropManager();
        }

        return instance;
    }

    // field
    private boolean isTouchable; // 是否响应按键事件，如果一个气泡已经在响应，其它气泡就不响应，同一界面始终最多只有一个气泡响应按键

    private int statusBarHeight; // 状态栏(通知栏)高度

    @Nullable
    private DropCover dropCover; // Drop全屏动画

    @Nullable
    private Object currentId; // 当前正在执行动画的业务节点

    @Nullable
    private TextPaint textPaint; // 文本画笔共享

    private float textYOffset; // 文本y轴居中需要的offset

    @Nullable
    private Paint circlePaint; // 圆形画笔共享

    @NonNull
    private int[] explosionResIds = new int[]{
            R.drawable.explosion_one,
            R.drawable.explosion_two,
            R.drawable.explosion_three,
            R.drawable.explosion_four,
            R.drawable.explosion_five
    };

    // interface
    public void init(Context context, DropCover dropCover, DropCover.IDropCompletedListener listener) {
        context = context.getApplicationContext();
        this.isTouchable = true;
        this.statusBarHeight = ScreenUtil.getStatusBarHeight(context);
        this.dropCover = dropCover;
        this.dropCover.addDropCompletedListener(listener);

    }

    public void initPaint() {
        getCirclePaint();
        getTextPaint();
    }

    public void destroy() {
        this.isTouchable = false;
        this.statusBarHeight = 0;
        this.dropCover.removeAllDropCompletedListeners();
        this.dropCover = null;
        this.currentId = null;
        this.textPaint = null;
        this.textYOffset = 0;
        this.circlePaint = null;

    }

    public boolean isTouchable() {
        return isTouchable;
    }

    public void setTouchable(boolean isTouchable) {
        this.isTouchable = isTouchable;
    }

    public int getTop() {
        return statusBarHeight;
    }

    @Nullable
    public DropCover getDropCover() {
        return this.dropCover;
    }

    public void setCurrentId(Object currentId) {
        this.currentId = currentId;
    }

    @Nullable
    public Object getCurrentId() {
        return currentId;
    }

    @Nullable
    public Paint getCirclePaint() {
        if (circlePaint == null) {
            circlePaint = new Paint();
            circlePaint.setColor(Color.RED);
            circlePaint.setAntiAlias(true);
        }

        return circlePaint;
    }

    @Nullable
    public TextPaint getTextPaint() {
        if (textPaint == null) {
            textPaint = new TextPaint();
            textPaint.setAntiAlias(true);
            textPaint.setColor(Color.WHITE);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(TEXT_SIZE);
            Paint.FontMetrics textFontMetrics = textPaint.getFontMetrics();

            /*
             * drawText从baseline开始，baseline的值为0，baseline的上面为负值，baseline的下面为正值，
             * 即这里ascent为负值，descent为正值。
             * 比如ascent为-20，descent为5，那需要移动的距离就是20 - （20 + 5）/ 2
             */
            textYOffset = -textFontMetrics.ascent - (-textFontMetrics.ascent + textFontMetrics.descent) / 2;
        }

        return textPaint;
    }

    public float getTextYOffset() {
        getTextPaint();
        return textYOffset;
    }

    @NonNull
    public int[] getExplosionResIds() {
        return explosionResIds;
    }
}
