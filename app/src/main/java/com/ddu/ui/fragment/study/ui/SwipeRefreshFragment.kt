package com.ddu.ui.fragment.study.ui

import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ddu.R
import com.ddu.app.BaseApp
import com.ddu.icore.ui.adapter.common.DefaultRVAdapter
import com.ddu.icore.ui.adapter.common.ViewHolder
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.icore.ui.adapter.common.HeaderOrFooterRecycleViewAdapter
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_swipe_refresh.*
import java.util.*

/**
 * Created by yzbzz on 16/5/6.
 */
@IElement("UI")
class SwipeRefreshFragment : DefaultFragment(), SwipeRefreshLayout.OnRefreshListener {

    private val mDatas = ArrayList<String>()

    private lateinit var mAdapter: DefaultRVAdapter<String>
    private lateinit var advanceRecycleViewAdapter: HeaderOrFooterRecycleViewAdapter

    override fun initData(savedInstanceState: Bundle?) {
        for (i in 0..1) {
            mDatas.add("i - " + i)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_swipe_refresh
    }

    override fun initView() {

        srl_swipe_refresh.setOnRefreshListener(this)
        srl_swipe_refresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light)

        rv_swipe_refresh.setHasFixedSize(true)
        rv_swipe_refresh.layoutManager = LinearLayoutManager(mContext)
        rv_swipe_refresh.itemAnimator = DefaultItemAnimator()
        mAdapter = object : DefaultRVAdapter<String>(mContext, mDatas) {
            override fun getLayoutId(viewType: Int): Int {
                return R.layout.rv_item_linear
            }

            override fun bindView(viewHolder: ViewHolder, data: String, position: Int) {
                viewHolder.setText(R.id.tv_title, data)
            }
        }

        advanceRecycleViewAdapter = HeaderOrFooterRecycleViewAdapter(mAdapter)
        val textView = TextView(mContext)
        textView.text = "Header"
        val textView1 = TextView(mContext)
        textView1.text = "Footer"
        val textView2 = TextView(mContext)
        textView2.text = "Footer"
        val textView3 = TextView(mContext)
        textView3.text = "Footer"
        advanceRecycleViewAdapter.addHeaderView(textView)
        advanceRecycleViewAdapter.addHeaderView(textView1)
        advanceRecycleViewAdapter.addFooterView(textView2)
        advanceRecycleViewAdapter.addFooterView(textView3)

        rv_swipe_refresh.adapter = advanceRecycleViewAdapter

        mAdapter.setEmptyView(R.layout.empty_view, rv_swipe_refresh)

        btn_ok.setOnClickListener {
            mDatas.clear()
            advanceRecycleViewAdapter.notifyDataSetChanged()
        }
    }

    override fun isShowTitleBar(): Boolean {
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onRefresh() {
        BaseApp.postDelayed(Runnable{
            val size = mDatas.size
            for (i in size until size + 10) {
                mDatas.add("i - " + i)
            }
            advanceRecycleViewAdapter.notifyDataSetChanged()
            srl_swipe_refresh.isRefreshing = false
        }, 2000)

    }

    companion object {

        private val TAG = "ARGUMENT_TASK_ID"

        fun newInstance(taskId: String): SwipeRefreshFragment {
            val arguments = Bundle()
            arguments.putString("ARGUMENT_TASK_ID", taskId)
            val fragment = SwipeRefreshFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
}
