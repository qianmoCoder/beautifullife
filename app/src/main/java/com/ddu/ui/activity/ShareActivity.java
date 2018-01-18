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
import com.ddu.icore.dialog.ShareAdapter;
import com.ddu.icore.entity.ShareEntity;
import com.ddu.icore.ui.activity.BaseActivity;
import com.ddu.icore.util.sys.ViewUtils;
import com.ddu.ui.dialog.LoginDialog;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import static com.ddu.icore.app.BaseApp.getContext;

/**
 * Created by yzbzz on 2017/4/6.
 */

public class ShareActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvCancel;
    private RecyclerView mRecyclerView;

    private List<ShareEntity> shareEntities;
    private ShareAdapter shareAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_share);

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

        ShareEntity shareEntity = new ShareEntity();
        shareEntity.setName("QQ");
        shareEntity.setResId(R.drawable.weixin_friend_share);

        ShareEntity shareEntity1 = new ShareEntity();
        shareEntity1.setName("weixin");
        shareEntity1.setResId(R.drawable.weixin_circle_friend_share);

        ShareEntity shareEntity2 = new ShareEntity();
        shareEntity2.setName("feixin");
        shareEntity2.setResId(R.drawable.weixin_friend_share);

        ShareEntity shareEntity3 = new ShareEntity();
        shareEntity3.setName("yixin");
        shareEntity3.setResId(R.drawable.weixin_circle_friend_share);

        ShareEntity shareEntity4 = new ShareEntity();
        shareEntity4.setName("weibo");
        shareEntity4.setResId(R.drawable.weixin_friend_share);

        shareEntities.add(shareEntity);
        shareEntities.add(shareEntity1);
        shareEntities.add(shareEntity2);

        mTvCancel = ViewUtils.findViewById(this, R.id.tv_cancel);
        mTvCancel.setOnClickListener(this);

        mRecyclerView = ViewUtils.findViewById(this, R.id.rv_share);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), shareEntities.size() < 4 ? shareEntities.size() % 4 : 4);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        shareAdapter = new ShareAdapter(getContext(), shareEntities);
        mRecyclerView.setAdapter(shareAdapter);

        shareAdapter.setOnItemClickListener(new ShareAdapter.OnClickListener<ShareEntity>() {
            @Override
            public void onClick(ShareEntity data, int position) {

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
        overridePendingTransition(R.anim.frament_alpha_in, R.anim.frament_alpha_out);
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
