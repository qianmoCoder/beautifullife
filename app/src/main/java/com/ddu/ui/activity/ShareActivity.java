package com.ddu.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ddu.R;
import com.ddu.app.BaseApp;
import com.ddu.icore.dialog.DefaultGridBottomAdapter;
import com.ddu.icore.entity.BottomItemEntity;
import com.ddu.icore.ui.activity.BaseActivity;
import com.ddu.icore.util.sys.ViewUtils;
import com.ddu.ui.dialog.LoginDialog;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzbzz on 2017/4/6.
 */

public class ShareActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvCancel;
    private RecyclerView mRecyclerView;

    private List<BottomItemEntity> shareEntities;
    private DefaultGridBottomAdapter shareAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_content);

        Window win = this.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setGravity(Gravity.BOTTOM);
        win.setAttributes(lp);

        init();
    }

    private void init() {

        shareEntities = new ArrayList<>();

        BottomItemEntity shareEntity = new BottomItemEntity();
        shareEntity.setName("QQ");
        shareEntity.setResId(R.drawable.weixin_friend_share);

        BottomItemEntity shareEntity1 = new BottomItemEntity();
        shareEntity1.setName("weixin");
        shareEntity1.setResId(R.drawable.weixin_circle_friend_share);

        BottomItemEntity shareEntity2 = new BottomItemEntity();
        shareEntity2.setName("feixin");
        shareEntity2.setResId(R.drawable.weixin_friend_share);

        BottomItemEntity shareEntity3 = new BottomItemEntity();
        shareEntity3.setName("yixin");
        shareEntity3.setResId(R.drawable.weixin_circle_friend_share);

        BottomItemEntity shareEntity4 = new BottomItemEntity();
        shareEntity4.setName("weibo");
        shareEntity4.setResId(R.drawable.weixin_friend_share);

        shareEntities.add(shareEntity);
        shareEntities.add(shareEntity1);
        shareEntities.add(shareEntity2);

        mTvCancel = ViewUtils.findViewById(this, R.id.tv_cancel);
        mTvCancel.setOnClickListener(this);

        mRecyclerView = ViewUtils.findViewById(this, R.id.rv_share);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(BaseApp.Companion.getContext(), shareEntities.size() < 4 ? shareEntities.size() % 4 : 4);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        shareAdapter = new DefaultGridBottomAdapter(BaseApp.Companion.getContext(), shareEntities);
        mRecyclerView.setAdapter(shareAdapter);

        shareAdapter.setOnItemClickListener(new DefaultGridBottomAdapter.OnClickListener<BottomItemEntity>() {
            @Override
            public void onClick(BottomItemEntity data, int position) {

//                UMWeb web = new UMWeb("http://www.baidu.com");
//                web.setTitle("this is music title");
//                web.setThumb(null);
//                web.setDescription("my description");
//                new ShareAction(ShareActivity.this).withMedia(web).setPlatform(SHARE_MEDIA.ALIPAY).share();
//
//                ToastUtils.showTextToast(data.getName() + " " + position);
//                finish();
                LoginDialog loginDialog = LoginDialog.newInstance();
                loginDialog.showDialog(ShareActivity.this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_cancel:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isShowTitleBar() {
        return false;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);

            Toast.makeText(getMContext(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getMContext(), platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getMContext(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
}
