package com.ddu.icore.util;

import android.graphics.Color;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;

import com.ddu.icore.callback.Consumer1;

/**
 * Created by yzbzz on 2019/1/21.
 */
public class HtmlUtils {

    private static void setLinkClickable(final Consumer1<String> consumer1, final SpannableStringBuilder clickableHtmlBuilder,
                                         final URLSpan urlSpan) {
        int start = clickableHtmlBuilder.getSpanStart(urlSpan);
        int end = clickableHtmlBuilder.getSpanEnd(urlSpan);
        int flags = clickableHtmlBuilder.getSpanFlags(urlSpan);
        ClickableSpan clickableSpan = new ClickableSpan() {

            public void onClick(View view) {
                //Do something with URL here.
                String url = urlSpan.getURL();
                consumer1.accept(url);
            }

            public void updateDrawState(TextPaint ds) {
                //设置颜色
                ds.setColor(Color.BLUE);
                //设置是否要下划线
                ds.setUnderlineText(false);
            }

        };
        clickableHtmlBuilder.setSpan(clickableSpan, start, end, flags);
    }


    public static CharSequence getClickableHtml(String html, Consumer1<String> consumer1) {
        Spanned spannedHtml = Html.fromHtml(html);
        SpannableStringBuilder clickableHtmlBuilder = new SpannableStringBuilder(spannedHtml);
        URLSpan[] urls = clickableHtmlBuilder.getSpans(0, spannedHtml.length(), URLSpan.class);
        for (final URLSpan span : urls) {
            setLinkClickable(consumer1, clickableHtmlBuilder, span);
        }
        return clickableHtmlBuilder;
    }
}
