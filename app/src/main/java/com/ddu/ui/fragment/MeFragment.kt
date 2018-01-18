package com.ddu.ui.fragment

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.SystemClock
import android.support.annotation.CheckResult
import android.support.annotation.StringRes
import android.text.TextPaint
import android.view.View
import android.widget.Toast
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.icore.util.AnimatorUtils
import kotlinx.android.synthetic.main.fragment_me.*

/**
 * Created by yzbzz on 2018/1/17.
 */
class MeFragment : DefaultFragment() {
    var mHits = LongArray(COUNTS)


    private val objectAnimator = arrayOfNulls<ObjectAnimator>(2)

    override fun getLayoutId(): Int {
        return R.layout.fragment_me
    }

    @CheckResult
    fun generateString(param: String): String {
        return param.replace(" ", "-")
    }

    fun sayHello(@StringRes resId: Int): String {
        return ""
    }


    override fun initView() {
        //        dropFake.set_text("3");
        //        dropFake.setClickListener(new DropFake.ITouchListener() {
        //            @Override
        //            public void onDown() {
        //                DropManager.getInstance().setCurrentId("0");
        //                DropManager.getInstance().getDropCover().down(dropFake, "3");
        //            }
        //
        //            @Override
        //            public void onMove(float curX, float curY) {
        //                DropManager.getInstance().getDropCover().move(curX, curY);
        //            }
        //
        //            @Override
        //            public void onUp() {
        //                DropManager.getInstance().getDropCover().up();
        //            }
        //        });
        //
        //        DropManager.getInstance().init(mContext, dropCover, new DropCover.IDropCompletedListener() {
        //            @Override
        //            public void onCompleted(Object id, boolean explosive) {
        //
        //            }
        //        });

        ll_setting.setOnClickListener(object : View.OnClickListener {

            internal var mHints = LongArray(3)

            override fun onClick(v: View) {
                //                int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                //                if (mode == Configuration.UI_MODE_NIGHT_YES) {
                //                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                //                } else if (mode == Configuration.UI_MODE_NIGHT_NO) {
                //                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                //                }
                //                baseActivity.recreate();

                //                ShareDialogFragment shareDialogFragment = ShareDialogFragment.newInstance();
                //                shareDialogFragment.show(getFragmentManager(), "h");
                //                startFragment(ToolBarFragment.class);

                //                System.arraycopy(mHints, 1, mHints, 0, mHints.length - 1);
                //                //获得当前系统已经启动的时间
                //                mHints[mHints.length - 1] = SystemClock.uptimeMillis();
                //                if (SystemClock.uptimeMillis() - mHints[0] <= 500)
                //                    Toast.makeText(mContext, "当你点击三次之后才会出现", Toast.LENGTH_SHORT).show();


                /**
                 * 实现双击方法
                 * src 拷贝的源数组
                 * srcPos 从源数组的那个位置开始拷贝.
                 * dst 目标数组
                 * dstPos 从目标数组的那个位子开始写数据
                 * length 拷贝的元素的个数
                 */
                System.arraycopy(mHits, 1, mHits, 0, mHits.size - 1)
                //实现左移，然后最后一个位置更新距离开机的时间，如果最后一个时间和最开始时间小于DURATION，即连续5次点击
                mHits[mHits.size - 1] = SystemClock.uptimeMillis()
                if (mHits[0] >= SystemClock.uptimeMillis() - DURATION) {
                    val tips = "您已在[" + DURATION + "]ms内连续点击【" + mHits.size + "】次了！！！"
                    Toast.makeText(mContext, tips, Toast.LENGTH_SHORT).show()
                }

            }
        })

        setTitle(R.string.main_tab_me)

        //        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(tvU.get_text());
        //        CharacterStyle span = new StrikethroughSpan();
        //        spannableStringBuilder.setSpan(span, tvU.get_text().length(), tvU.get_text().length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        val paint = TextPaint()
        paint.color = Color.RED
        paint.strokeWidth = 3f
        paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        tvU!!.paint.set(paint)
        et_text.text.clear()

        rl_person_info.setOnClickListener({
            val intent = Intent(Intent.ACTION_SEND)
            val title = "today"
            val chooser = Intent.createChooser(intent, title)
            if (intent.resolveActivity(mContext.packageManager) != null) {
                startActivity(chooser)
            }
            //        int color = getResources().getColor(R.color.c_4897fa);
            //        int c = Color.parseColor("#4897fa");
            //        Log.v("lhz","color: " + color);
            //        Log.v("lhz", "c: " + c);
            //        animator();
            //        Bundle bundle = new Bundle();
            //        bundle.putString("mTitle", "163");
            //        bundle.putString("url", "http://www.163.com");
            //        baseActivity.startFragment(WebFragment.class, bundle);
            //        DialogUtils.createLoadingDialog(mContext).show();
            //        DialogUtils.showDialog(mContext, R.drawable.toast_right_icon, "Hello", "OK", new DialogUtils.ITwoButtonListener() {
            //            @Override
            //            public void onBtnCancelClickListener(Dialog dialog) {
            //                dialog.dismiss();
            //            }
            //
            //            @Override
            //            public void onBtnClickListener(Dialog dialog) {
            //                dialog.dismiss();
            //            }
            //        });
        })
    }

    private fun animator() {
        iv_guide_delete!!.visibility = View.VISIBLE
        objectAnimator[0] = AnimatorUtils.translation(iv_guide_hand, 1500, 0f, -100f)
        objectAnimator[1] = AnimatorUtils.translation(iv_guide_delete, 1500, 100f, 0f)
        val animation = AnimatorSet()
        animation.playTogether(*objectAnimator)
        animation.start()
        animation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                iv_guide_delete!!.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })
        //        iv_guide_delete.setVisibility(View.INVISIBLE);
        //        BaseApp.Companion().postDelayed(new Runnable() {
        //            @Override
        //            public void run() {
        //                animation.start();
        //            }
        //        }, 500);


    }

    companion object {

        internal val COUNTS = 10//点击次数
        internal val DURATION = (3 * 1000).toLong()//规定有效时间

        fun newInstance(): MeFragment {
            val fragment = MeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}