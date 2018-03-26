package com.ddu.ui.fragment

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.icore.util.TextPhrase
import com.ddu.ui.fragment.life.IncomeTaxFragment
import com.ddu.ui.fragment.life.MortgageFragment
import kotlinx.android.synthetic.main.fragment_life.*

/**
 * Created by yzbzz on 2018/1/17.
 */
class LifeFragment : DefaultFragment(), View.OnClickListener {

    companion object {
        fun newInstance(): LifeFragment {
            val fragment = LifeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_life
    }

    override fun initView() {
        tvHelp.movementMethod = LinkMovementMethod.getInstance()
        initText()
        setTitle(R.string.main_tab_life)
        //        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_b);
        //        RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        //        bitmapDrawable.setCornerRadius(10);
        //        iv_icon.setImageDrawable(bitmapDrawable);


        //        GlideApp.with(this).load(R.drawable.icon_b).into(new ImageViewTarget<Drawable>(iv_icon) {
        //            @Override
        //            protected void setResource(@Nullable Drawable resource) {
        //                Log.v("lhz", "setResource");
        //                iv_icon.setImageDrawable(resource);
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

        rl_life_calculator_house.setOnClickListener(this)
        rl_life_calculator_income.setOnClickListener(this)
        rl_joke.setOnClickListener(this)


    }//        String html = "<html><head><mTitle>tvHelp使用HTML</mTitle></head><body><p><strong>强调</strong></p><p><em>斜体</em></p>\"  \n" +
    //                "                +\"<p><a href=\\\"Icore://details?id=3/\\\">超链接HTML入门</a>学习HTML!</p><p><font color=\\\"#aabb00\\\">颜色1\"  \n" +
    //                "                +\"</p><p><font color=\\\"#00bbaa\\\">颜色2</p><h1>标题1</h1><h3>标题2</h3><h6>标题3</h6><p>大于>小于<</p><p>\" +  \n" +
    //                "                \"下面是网络图片</p><img src=\\\"http://avatar.csdn.net/0/3/8/2_zhang957411207.jpg\\\"/></body></html>";
    //        tvHelp.setText(Html.fromHtml(html));

    private fun initText() {

        val textPhrase1 = TextPhrase(mContext.getString(R.string.parse1))
        parse(textPhrase1)

        val textPhrase2 = TextPhrase(mContext.getString(R.string.parse2))
        textPhrase2.firstSeparator = "()"
        parse(textPhrase2)

        val textPhrase3 = TextPhrase(mContext.getString(R.string.parse3))
        textPhrase3.secondSeparator = "^"
        parse(textPhrase3)

        tvHelp1.text = textPhrase1.format()
        tvHelp2.text = textPhrase2.format()
        tvHelp3.text = textPhrase3.format()

    }

    private fun parse(textPhrase: TextPhrase) {
        textPhrase.innerFirstColor = -0x10000
        textPhrase.innerFirstSize = 20

        textPhrase.innerSecondColor = -0xb76806
        textPhrase.innerSecondSize = 30
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.rl_life_calculator_house -> startFragment(MortgageFragment::class.java)
            R.id.rl_life_calculator_income -> startFragment(IncomeTaxFragment::class.java)
            R.id.rl_joke -> {
                val bundle = Bundle()
                bundle.putString("mTitle", "测试")
                bundle.putString("url", "http://www.wdxhb.com/m/Icorejsapi.html")
                startFragment(WebFragment::class.java)
            }
        }
    }
}