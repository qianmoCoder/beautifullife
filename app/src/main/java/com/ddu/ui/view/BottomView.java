package com.ddu.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ddu.R;

public class BottomView {
    private View contentView;
    private Context context;
    private int theme;
    private Dialog bv;
    private int animationStyle;
    private boolean isTop = false;

    public BottomView(Context c) {
        this.theme = R.style.BottomViewThemeDefalut;
        this.context = c;
    }

    public BottomView(Context c, int theme) {
        this.theme = theme;
        this.context = c;
    }

    public BottomView(Context c, int theme, View convertView) {
        this.theme = theme;
        this.context = c;
        this.contentView = convertView;
    }

    public BottomView(Context c, int theme, int resource) {
        this.theme = theme;
        this.context = c;
        this.contentView = View.inflate(c, resource, null);
    }

    public void setOnCancelListener(@Nullable DialogInterface.OnCancelListener cancelListener) {
        if (cancelListener != null) {
            bv.setOnCancelListener(cancelListener);
        }
    }

    public void showBottomView(boolean CanceledOnTouchOutside) {
        if (this.bv == null) {
            if (this.theme == 0)
                this.bv = new Dialog(this.context);
            else
                this.bv = new Dialog(this.context, this.theme);


            this.bv.setCanceledOnTouchOutside(CanceledOnTouchOutside);
            this.bv.getWindow().requestFeature(1);

            this.bv.setContentView(this.contentView);
            Window wm = this.bv.getWindow();

            wm.setBackgroundDrawableResource(android.R.color.transparent);

            WindowManager m = wm.getWindowManager();
            Display d = m.getDefaultDisplay();
            WindowManager.LayoutParams p = wm.getAttributes();
            p.width = (d.getWidth() * 1);
            if (this.isTop)
                p.gravity = 48;
            else
                p.gravity = 80;

            if (this.animationStyle != 0) {
                wm.setWindowAnimations(this.animationStyle);
            }
            wm.setAttributes(p);
        }
        this.bv.show();
    }

    public void setContentView(View contentView) {
        this.contentView = contentView;
    }

    public void setTopIfNecessary() {
        this.isTop = true;
    }

    public void setAnimation(int animationStyle) {
        this.animationStyle = animationStyle;
    }

    public View getView() {
        return this.contentView;
    }

    public void dismissBottomView() {
        if (this.bv != null)
            this.bv.dismiss();
    }

    /*
     是否展示
     */
    public boolean isViewShow() {
        if (this.bv != null) {
            if (this.bv.isShowing()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}