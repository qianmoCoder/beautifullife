package com.ddu.icore.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

/**
 * Created by yzbzz on 2017/1/17.
 */

public class RenderScriptGaussianBlur {
    private RenderScript renderScript;

    public RenderScriptGaussianBlur(@NonNull Context context) {
        this.renderScript = RenderScript.create(context);
    }

    public Bitmap gaussianBlur(@IntRange(from = 1, to = 25) int radius, Bitmap original) {
        Allocation input = Allocation.createFromBitmap(renderScript, original);
        Allocation output = Allocation.createTyped(renderScript, input.getType());
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setRadius(radius);
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.forEach(output);
        output.copyTo(original);
        return original;
    }
}
