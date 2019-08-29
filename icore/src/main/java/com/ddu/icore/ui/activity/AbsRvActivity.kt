package com.ddu.icore.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ddu.icore.R
import com.ddu.icore.common.ext.ctx
import com.ddu.icore.ui.adapter.common.DefaultRVAdapter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.i_activity_rv_default.*

/**
 * Created by yzbzz on 2017/4/13.
 */

abstract class AbsRvActivity<D, A : DefaultRVAdapter<D>> : BaseActivity(), OnLoadMoreListener {

    lateinit var mAdapter: A

    var mItems = mutableListOf<D>()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView()
    }

    fun initView() {
        rv_default.layoutManager = LinearLayoutManager(ctx)
        rv_default.setHasFixedSize(true)
        rv_default.isNestedScrollingEnabled = false
        mAdapter = getAdapter()
        rv_default.adapter = mAdapter
        getItemDecoration()?.let {
            rv_default.addItemDecoration(it)
        }
        initRefreshView()
    }

    fun initRefreshView() {
        refresh_view_default.setEnableRefresh(false)
        refresh_view_default.setEnableLoadMore(true)
        refresh_view_default.setOnLoadMoreListener(this)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
    }


    fun getItemDecoration(): RecyclerView.ItemDecoration? {
        return DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL)
    }

    fun getLayoutId() = R.layout.i_activity_rv_default

    abstract fun getAdapter(): A
}
