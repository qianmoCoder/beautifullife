package com.ddu.ui.fragment.work

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.icore.util.AnimatorUtils
import kotlinx.android.synthetic.main.fragment_me.*

/**
 * Created by lhz on 16/4/6.
 */
class FragmentState : DefaultFragment(), View.OnClickListener {

    private val objectAnimator = arrayOfNulls<ObjectAnimator>(2)

    override fun getLayoutId(): Int {
        return R.layout.fragment_me
    }

    override fun initView() {
        //        dropFake.setText("3");
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

        setDefaultTitle("我")
        rl_person_info.setOnClickListener(this)
    }

    override fun onClick(view: View) {
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
    }

    private fun animator() {
        iv_guide_delete.setVisibility(View.VISIBLE)
        objectAnimator[0] = AnimatorUtils.translation(iv_guide_hand, 1500, 0f, -100f)
        objectAnimator[1] = AnimatorUtils.translation(iv_guide_delete, 1500, 100f, 0f)
        val animation = AnimatorSet()
        animation.playTogether(*objectAnimator)
        animation.start()
        animation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                iv_guide_delete.setVisibility(View.GONE)
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

        fun newInstance(): FragmentState {
            val fragment = FragmentState()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
