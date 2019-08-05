package com.ddu.ui.fragment.study.ui;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.ui.widget.ClockLoadingView;
import com.ddu.icore.ui.widget.DownloadView;
import com.ddu.widget.TransparentCircleView;
import com.iannotation.IElement;

/**
 * Created by yzbzz on 2018/6/8.
 */
@IElement("customer")
public class StudyViewFragment extends DefaultFragment {

    private ViewGroup mSVGroup;
    private View mView;

    private DownloadView dlv;
    private ClockLoadingView loading_clock_clv;

    private int count = 0;

    private Button btn_shake;
    private Button btn_shake1;
    private Button btn_shake2;


    @Override
    public int getLayoutId() {
        return R.layout.study_view;
    }

    @Override
    public void initView() {
        mSVGroup = findViewById(R.id.sv_group);
        mView = findViewById(R.id.sv_view);
        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.v("lhz", "onTouch: " + event.getAction());
                return false;
            }
        });
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("lhz", "onClick: ");
            }
        });

        dlv = findViewById(R.id.dlv_process);
        dlv.setBgColor(Color.parseColor("#cccccc"));

        Message m = Message.obtain();
        m.what = 1;
        handler.sendMessageDelayed(m, 1000);

        loading_clock_clv = findViewById(R.id.loading_clock_clv);
        loading_clock_clv.post(new Runnable() {
            @Override
            public void run() {
                loading_clock_clv.start();
            }
        });

        final int snakeExcursion = (int) getResources().getDimension(R.dimen.dp_20);


        final TransparentCircleView t = findViewById(R.id.tcv);
        btn_shake = findViewById(R.id.btn_shake);
        btn_shake1 = findViewById(R.id.btn_shake_1);
        btn_shake2 = findViewById(R.id.btn_shake_2);
        btn_shake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator snake_objectAnimator = ObjectAnimator.ofFloat(btn_shake1, "translationX", 0, snakeExcursion, -snakeExcursion, snakeExcursion, -snakeExcursion, snakeExcursion, 0);
                snake_objectAnimator.setInterpolator(new LinearInterpolator());
                snake_objectAnimator.setDuration(3000);
                snake_objectAnimator.start();

                ObjectAnimator snake_objectAnimator1 = ObjectAnimator.ofFloat(btn_shake2, "translationX", 0, snakeExcursion);
                snake_objectAnimator1.setDuration(3000);
                snake_objectAnimator1.setInterpolator(new CycleInterpolator(3));
                snake_objectAnimator1.start();

                t.startAnim();
            }
        });


    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (count <= 100) {
                count += 10;
                Message m = new Message();
                m.what = 1;
                handler.sendMessageDelayed(m, 1000);
            } else {
                handler.removeMessages(1);
            }
            dlv.setProgress(count);
        }
    };
}
