package com.ddu.ui.fragment.study.imitate

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import com.ddu.R
import com.ddu.icore.ui.adapter.common.DefaultListViewAdapter
import com.ddu.icore.ui.adapter.common.ViewHolder
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.icore.util.FragmentUtils
import com.ddu.ui.fragment.study.ui.DrawViewFragment
import com.ddu.ui.fragment.study.ui.ShapeFragment
import com.iannotation.Element
import kotlinx.android.synthetic.main.fragment_ui.*

/**
 * Created by yzbzz on 16/4/8.
 */
@Element("UI")
class UIFragment : DefaultFragment(), AdapterView.OnItemClickListener {

    private val mLayoutManager: RecyclerView.LayoutManager? = null

    private var mList: MutableList<Fragment> = mutableListOf()

    init {
        mList.add(ShapeFragment.newInstance(""))
        mList.add(ShapeFragment.newInstance(""))
        mList.add(ShapeFragment.newInstance(""))
        mList.add(ShapeFragment.newInstance(""))
        mList.add(ShapeFragment.newInstance(""))
        mList.add(ShapeFragment.newInstance(""))
        mList.add(ShapeFragment.newInstance(""))
        mList.add(ShapeFragment.newInstance(""))
        mList.add(ShapeFragment.newInstance(""))
        mList.add(ShapeFragment.newInstance(""))
        mList.add(ShapeFragment.newInstance(""))
        mList.add(ShapeFragment.newInstance(""))
        mList.add(ShapeFragment.newInstance(""))
        mList.add(ShapeFragment.newInstance(""))
        mList.add(ShapeFragment.newInstance(""))
        mList.add(ShapeFragment.newInstance(""))
        mList.add(DrawViewFragment::class.java.newInstance())
        mList.add(DrawViewFragment::class.java.newInstance())
    }


    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_ui
    }

    override fun initView() {
        lv_ui_navigation.adapter = object : DefaultListViewAdapter<Fragment>(mContext, mList) {
            override fun bindView(position: Int, s: Fragment, holder: ViewHolder) {
                holder.setText(R.id.tv_navi_name, s::class.java.simpleName)
            }

            override fun getLayoutId(): Int {
                return R.layout.fragment_ui_navi_item
            }
        }

        lv_ui_navigation.onItemClickListener = this
        setDefaultTitle("UI")
    }


    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        ls_ui_navigation!!.smoothScrollTo(0, position * view.height)
        val fragment = parent.adapter.getItem(position) as? Fragment
        fragment?.apply {
            FragmentUtils.replaceFragment(baseActivity.supportFragmentManager, fragment, R.id.fl_ui_detail)
        }
    }
}
