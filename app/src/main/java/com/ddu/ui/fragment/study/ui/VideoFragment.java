package com.ddu.ui.fragment.study.ui;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.ddu.R;
import com.ddu.app.App;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.util.AnimatorUtils;
import com.ddu.icore.util.ToastUtils;

import static com.umeng.socialize.utils.ContextUtil.getPackageName;

/**
 * Created by yzbzz on 2017/6/22.
 */

public class VideoFragment extends DefaultFragment {

    private VideoView mVideoView;

    private Button btnOk;
    private LinearLayout llOther;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ui_video;
    }

    @Override
    public void initView() {
        llOther = findViewById(R.id.ll_other);
        btnOk = findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("hello");
            }
        });
        mVideoView = findViewById(R.id.surface_view);
        mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() +
                "/" + R.raw.videoviewdemo));
//        mVideoView.setMediaController(new MediaController(mContext));
        mVideoView.requestFocus();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.v("lhz", " onPrepared ");
                mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mp, int percent) {
                        int currentPosition = mVideoView.getCurrentPosition();
                        int duration = mVideoView.getDuration();
                        Log.v("lhz", " current " + currentPosition + " " + duration);
                    }
                });
                mVideoView.start();
                mp.setLooping(true);
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.v("lhz", " onCompletion ");
                mVideoView.start();
            }
        });

        App.getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                llOther.setVisibility(View.VISIBLE);
                AnimatorUtils.alpha(llOther, 1000, 0, 1).start();
            }
        }, 5000);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
    }

//    private String stringForTime(int timeMs) {
//        int totalSeconds = timeMs / 1000;
//
//        int seconds = totalSeconds % 60;
//        int minutes = (totalSeconds / 60) % 60;
//        int hours   = totalSeconds / 3600;
//
//        mFormatBuilder.setLength(0);
//        if (hours > 0) {
//            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
//        } else {
//            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
//        }
//    }

    @Override
    public boolean isShowTitleBar() {
        return false;
    }
}
