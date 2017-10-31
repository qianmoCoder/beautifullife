package com.ddu.ui.viewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.lang.ref.WeakReference;

/**
 * Created by yzbzz on 2017/10/13.
 */

public class GodBanner extends ViewPager {

    private IntervalHandler mIntervalHandler;

    public GodBanner(Context context) {
        this(context, null);
    }

    public GodBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mIntervalHandler = new IntervalHandler(this);
    }

    private static class IntervalHandler extends Handler {
        static final int MESSAGE_CHECK = 9001;
        private WeakReference<GodBanner> innerObject;

        IntervalHandler(GodBanner context) {
            this.innerObject = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
//            if (MESSAGE_CHECK == msg.what) {
//                GodBanner GodBanner = innerObject.get();
//                if (GodBanner == null)
//                    return;
//                if (GodBanner.getContext() instanceof Activity) {
//                    Activity activity = (Activity) GodBanner.getContext();
//                    if (activity.isFinishing())
//                        return;
//                }
//                GodBanner.showNextView();
//
//                removeMessages(MESSAGE_CHECK);
//                sendMessageDelayed(obtainMessage(MESSAGE_CHECK), GodBanner.interval);
//                return;
//            }
            super.handleMessage(msg);
        }

    }
}
