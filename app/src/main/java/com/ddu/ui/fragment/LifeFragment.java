package com.ddu.ui.fragment;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.util.TextPhrase;
import com.ddu.ui.fragment.life.IncomeTaxFragment;
import com.ddu.ui.fragment.life.MortgageFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by yzbzz on 16/4/6.
 */
public class LifeFragment extends DefaultFragment {

    @Nullable
    @BindView(R.id.rl_life_calculator_house)
    RelativeLayout rlLifeCalculatorHouse;
    @Nullable
    @BindView(R.id.rl_life_calculator_income)
    RelativeLayout rlLifeCalculatorIncome;
    @Nullable
    @BindView(R.id.rl_joke)
    RelativeLayout rlJoke;
    @Nullable
    @BindView(R.id.tvHelp)
    TextView textView;

    @Nullable
    @BindView(R.id.tvHelp1)
    TextView textView1;

    @Nullable
    @BindView(R.id.tvHelp2)
    TextView textView2;

    @Nullable
    @BindView(R.id.tvHelp3)
    TextView textView3;

    @BindView(R.id.iv_icon)
    ImageView ivIcon;

    private Unbinder unbinder;

    @NonNull
    public static LifeFragment newInstance() {
        LifeFragment fragment = new LifeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_life;
    }

    @Override
    public void initView() {
        unbinder = ButterKnife.bind(this, mView);
//        String html = "<html><head><title>TextView使用HTML</title></head><body><p><strong>强调</strong></p><p><em>斜体</em></p>\"  \n" +
//                "                +\"<p><a href=\\\"Icore://details?id=3/\\\">超链接HTML入门</a>学习HTML!</p><p><font color=\\\"#aabb00\\\">颜色1\"  \n" +
//                "                +\"</p><p><font color=\\\"#00bbaa\\\">颜色2</p><h1>标题1</h1><h3>标题2</h3><h6>标题3</h6><p>大于>小于<</p><p>\" +  \n" +
//                "                \"下面是网络图片</p><img src=\\\"http://avatar.csdn.net/0/3/8/2_zhang957411207.jpg\\\"/></body></html>";
//        textView.setText(Html.fromHtml(html));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        initText();
        setTitle(R.string.main_tab_life);
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.request(Manifest.permission.CALL_PHONE).subscribe();
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_b);
//        RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
//        bitmapDrawable.setCornerRadius(10);
//        ivIcon.setImageDrawable(bitmapDrawable);


//        GlideApp.with(this).load(R.drawable.icon_b).into(new ImageViewTarget<Drawable>(ivIcon) {
//            @Override
//            protected void setResource(@Nullable Drawable resource) {
//                Log.v("lhz", "setResource");
//                ivIcon.setImageDrawable(resource);
////                if (null != defaultDialogFragment) {
////                    defaultDialogFragment.dismissAllowingStateLoss();
////                }
//            }
//
//            @Override
//            public void onLoadStarted(@Nullable Drawable placeholder) {
//                super.onLoadStarted(placeholder);
//                Log.v("lhz", "onLoadStarted");
//            }
//
//            @Override
//            public void onStart() {
//                super.onStart();
//                Log.v("lhz", "onStart");
//            }
//        });

    }

    private void initText() {
        TextPhrase textPhrase1 = TextPhrase.from(mContext, R.string.parse1);
        parse(textPhrase1);

        TextPhrase textPhrase2 = TextPhrase.from(mContext, R.string.parse2);
        textPhrase2.firstSeparator("()");
        parse(textPhrase2);

        TextPhrase textPhrase3 = TextPhrase.from(mContext, R.string.parse3);
        textPhrase3.secondSeparator("^^");
        parse(textPhrase3);

        textView1.setText(textPhrase1.format());
        textView2.setText(textPhrase2.format());
        textView3.setText(textPhrase3.format());

    }

    private void parse(@NonNull TextPhrase textPhrase) {
        textPhrase.innerFirstColor(0xffff0000);
        textPhrase.innerFirstSize(15);

        textPhrase.innerSecondColor(0xff4897fa);
        textPhrase.innerSecondSize(20);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.rl_life_calculator_house, R.id.rl_life_calculator_income, R.id.rl_joke})
    public void onClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.rl_life_calculator_house:
                startFragment(MortgageFragment.class);
                break;
            case R.id.rl_life_calculator_income:
                startFragment(IncomeTaxFragment.class);
                break;
            case R.id.rl_joke:
                Bundle bundle = new Bundle();
                bundle.putString("title", "测试");
                bundle.putString("url", "http://www.wdxhb.com/m/Icorejsapi.html");
                startFragment(WebFragment.class);
                break;
        }
    }

}
