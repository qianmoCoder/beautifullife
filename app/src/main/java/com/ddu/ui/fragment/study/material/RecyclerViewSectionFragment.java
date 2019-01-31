package com.ddu.ui.fragment.study.material;

import android.os.Bundle;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.model.Performer;
import com.ddu.ui.adapter.PerformerListAdapter;
import com.ddu.widget.StickyItemDecoration;
import com.iannotation.IElement;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by yzbzz on 2017/3/31.
 */
@IElement("MD")
public class RecyclerViewSectionFragment extends DefaultFragment {

    protected SmartRefreshLayout mPullToRefreshScrollView;

    protected RecyclerView mRvDefault;
    protected LinearLayoutManager mLinearLayoutManager;


    protected PerformerListAdapter mAdapter;

    protected RecyclerView.ItemDecoration mItemDecoration;

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_pinned_section;
    }

    @Override
    public void initView() {

        mPullToRefreshScrollView = findViewById(com.ddu.icore.R.id.default_refresh_view);

        mLinearLayoutManager = new LinearLayoutManager(getMContext(), RecyclerView.VERTICAL, false);
        mRvDefault = findViewById(com.ddu.icore.R.id.rl_default);
        mRvDefault.setLayoutManager(mLinearLayoutManager);
        mRvDefault.setHasFixedSize(true);
        mRvDefault.setNestedScrollingEnabled(false);

        mItemDecoration = getItemDecoration();

        if (null != mItemDecoration) {
            mRvDefault.addItemDecoration(mItemDecoration);
        }

        mRvDefault.addItemDecoration(new StickyItemDecoration());

        mAdapter = new PerformerListAdapter(mContext, getData());
        mRvDefault.setAdapter(mAdapter);

        mPullToRefreshScrollView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(2000);
            }
        });
        mPullToRefreshScrollView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000);
            }
        });
    }

    public RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(getMContext(), DividerItemDecoration.VERTICAL);
    }

    private List<Performer> getData() {
        List<Performer> performers = new ArrayList<>();

        Performer performer = new Performer("香港明星");
        performers.add(performer);

        Performer ldh = new Performer("刘德华", 10);
        performers.add(ldh);
        Performer zxy = new Performer("张学友", 10);
        performers.add(zxy);
        Performer zrf = new Performer("周润发", 10);
        performers.add(zrf);
        Performer lcw = new Performer("梁朝伟", 10);
        performers.add(lcw);
        Performer wyj = new Performer("吴毅将", 10);
        performers.add(wyj);
        Performer lm = new Performer("黎明", 10);
        performers.add(lm);
        Performer cgx = new Performer("陈冠希", 10);
        performers.add(cgx);
        Performer gfc = new Performer("郭富城", 10);
        performers.add(gfc);
        Performer xtf = new Performer("谢霆锋", 10);
        performers.add(xtf);

        Performer performerTw = new Performer("台湾明星：指的是中国台湾的一些有名气的电影，电视演员和歌手，他们具有很高的人气，成名时间早，成名时间久");
        performers.add(performerTw);

        Performer rxq = new Performer("任贤齐", 10);
        performers.add(rxq);
        Performer mtw = new Performer("孟庭苇", 10);
        performers.add(mtw);

        Performer performerTw2 = new Performer("台湾新生代明星：指的是中国台湾的一些有名气的电影，电视演员和歌手，他们具有很高的人气，成名时间早，成名时间久");
        performers.add(performerTw2);

        Performer rxq2 = new Performer("罗志祥", 10);
        performers.add(rxq2);

        Performer performerTw3 = new Performer("台湾明星：指的是中国台湾的一些有名气的电影，电视演员和歌手，他们具有很高的人气，成名时间早，成名时间久");
        performers.add(performerTw3);

        Performer rxq3 = new Performer("李宗盛", 10);
        performers.add(rxq3);

        Performer performerNl = new Performer("内陆明星");
        performers.add(performerNl);

        Performer lh = new Performer("鹿晗", 10);
        performers.add(lh);
        Performer wzw = new Performer("王志文", 10);
        performers.add(wzw);
        Performer yq = new Performer("羽泉", 10);
        performers.add(yq);
        Performer lxl = new Performer("李小璐", 10);
        performers.add(lxl);
        Performer hh = new Performer("韩红", 10);
        performers.add(hh);
        Performer ny = new Performer("那英", 10);
        performers.add(ny);
        Performer lhh = new Performer("刘欢", 10);
        performers.add(lhh);
        Performer yk = new Performer("杨坤", 10);
        performers.add(yk);
        Performer zj = new Performer("周杰", 10);
        performers.add(zj);

        Performer performerOm = new Performer("美国明星");
        performers.add(performerOm);
        Performer mm = new Performer("梅梅", 10);
        performers.add(mm);
        Performer ade = new Performer("Gaga", 10);
        performers.add(ade);
        Performer hff = new Performer("黑寡妇", 10);
        performers.add(hff);
        Performer xlz = new Performer("小李子", 10);
        performers.add(xlz);

        Performer performerNba = new Performer("NBA明星");
        performers.add(performerNba);
        Performer xhd = new Performer("小皇帝", 10);
        performers.add(xhd);
        Performer kb = new Performer("科比", 10);
        performers.add(kb);
        Performer ym = new Performer("姚明", 10);
        performers.add(ym);
        Performer md = new Performer("麦迪", 10);
        performers.add(md);
        Performer dlt = new Performer("杜兰特", 10);
        performers.add(dlt);
        Performer kl = new Performer("库里", 10);
        performers.add(kl);
        Performer ouw = new Performer("欧文", 10);
        performers.add(ouw);
        Performer qd = new Performer("乔丹", 10);
        performers.add(qd);
        Performer alzw = new Performer("奥拉朱旺", 10);
        performers.add(alzw);
        Performer pp = new Performer("皮蓬", 10);
        performers.add(pp);
        Performer ldm = new Performer("罗德曼", 10);
        performers.add(ldm);
        Performer ke = new Performer("科尔", 10);
        performers.add(ke);
        Performer pesi = new Performer("皮尔斯", 10);
        performers.add(pesi);
        Performer jnt = new Performer("加内特", 10);
        performers.add(jnt);
        Performer lal = new Performer("雷阿伦", 10);
        performers.add(lal);
        Performer zmg = new Performer("字母哥", 10);
        performers.add(zmg);
        Performer adn = new Performer("安东尼", 10);
        performers.add(adn);

        Performer performerDy = new Performer("导演");
        performers.add(performerDy);
        Performer jzk = new Performer("贾樟柯", 10);
        performers.add(jzk);
        Performer ly = new Performer("李杨", 10);
        performers.add(ly);
        Performer fxg = new Performer("冯小刚", 10);
        performers.add(fxg);
        Performer lyy = new Performer("娄烨", 10);
        performers.add(lyy);
        Performer zym = new Performer("张艺谋", 10);
        performers.add(zym);

        return performers;
    }


}
