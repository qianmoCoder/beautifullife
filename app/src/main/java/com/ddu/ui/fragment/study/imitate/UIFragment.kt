package com.ddu.ui.fragment.study.imitate

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import com.ddu.R
import com.ddu.icore.ui.adapter.common.DefaultListViewAdapter
import com.ddu.icore.ui.adapter.common.ViewHolder
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.icore.util.FragmentUtils
import com.ddu.ui.fragment.study.ui.ShapeFragment
import kotlinx.android.synthetic.main.fragment_ui.*
import java.util.*

/**
 * Created by yzbzz on 16/4/8.
 */
class UIFragment : DefaultFragment(), AdapterView.OnItemClickListener {

    private val mLayoutManager: RecyclerView.LayoutManager? = null

    private var mList: MutableList<String>? = null


    override fun initData(savedInstanceState: Bundle?) {
        mList = ArrayList()
        for (i in 0..19) {
            mList!!.add(i.toString() + "")
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_ui
    }

    override fun initView() {
        lv_ui_navigation.adapter = object : DefaultListViewAdapter<String>(mContext, mList) {
            override fun bindView(position: Int, s: String, holder: ViewHolder) {
                holder.setText(R.id.tv_navi_name, s)
            }

            override fun getLayoutId(): Int {
                return R.layout.fragment_ui_navi_item
            }
        }

        lv_ui_navigation!!.onItemClickListener = this
        setDefaultTitle("UI")
    }

    fun initTitle() {
        setDefaultTitle("UI")
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        ls_ui_navigation!!.smoothScrollTo(0, position * view.height)
        val shapeFragment = ShapeFragment.newInstance("")
        FragmentUtils.replaceFragment(baseActivity.supportFragmentManager, shapeFragment, R.id.fl_ui_detail)
    }
}
