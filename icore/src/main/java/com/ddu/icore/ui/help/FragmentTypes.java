package com.ddu.icore.ui.help;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.ddu.icore.util.FragmentUtils.FRAGMENT_ADD;
import static com.ddu.icore.util.FragmentUtils.FRAGMENT_ADD_TO_BACK_STACK;
import static com.ddu.icore.util.FragmentUtils.FRAGMENT_REPLACE;

/**
 * Created by yzbzz on 2016/12/27.
 */
@IntDef({INSTANCE.getFRAGMENT_ADD(), INSTANCE.getFRAGMENT_REPLACE(), INSTANCE.getFRAGMENT_ADD_TO_BACK_STACK()})
@Retention(RetentionPolicy.SOURCE)
public @interface FragmentTypes {
}
