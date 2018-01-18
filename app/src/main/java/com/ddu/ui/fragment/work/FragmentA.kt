package com.ddu.ui.fragment.work

import android.os.Bundle
import android.widget.Button
import com.ddu.R
import com.ddu.icore.ui.activity.ShowDetailActivity
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.icore.ui.view.CustomerTimeLineMarker
import com.ddu.icore.util.FragmentUtils
import com.ddu.icore.util.sys.ViewUtils
import kotlinx.android.synthetic.main.fragment_me.*

/**
 * Created by lhz on 16/4/6.
 */
class FragmentA : DefaultFragment() {

    private var mBtnOk: Button? = null
    private var button: Button? = null

    private var item_time_line_mark: CustomerTimeLineMarker? = null

    override fun initData(savedInstanceState: Bundle?) {


    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_work_state
    }

    override fun initView() {
        mBtnOk = ViewUtils.findViewById(mView, R.id.btn_ok)
        button = ViewUtils.findViewById(mView, R.id.button)
        item_time_line_mark = ViewUtils.findViewById(mView, R.id.item_time_line_mark)
        button!!.setOnClickListener {
            //                ShareDialogFragment bottomSheetDialogFragment = new ShareDialogFragment();
            //                bottomSheetDialogFragment.show(getFragmentManager(), "");
            //                replaceFragment(FragmentB.newInstance());
            (mActivity as ShowDetailActivity).replaceFragment(FragmentB.newInstance(), FragmentUtils.FRAGMENT_ADD_TO_BACK_STACK)
        }
        mBtnOk!!.setOnClickListener {
            val f = FragmentB.newInstance()
            //                replaceFragment(f);
            item_time_line_mark!!.count = 2
        }
        setDefaultTitle("FragmentA")
        rl_person_info.setOnClickListener({

        })
    }

    companion object {

        fun newInstance(): FragmentA {
            val fragment = FragmentA()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
