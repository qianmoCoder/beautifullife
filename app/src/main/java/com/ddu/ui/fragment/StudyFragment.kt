package com.ddu.ui.fragment

import android.os.Bundle
import com.ddu.icore.callback.Consumer1
import com.ddu.icore.refresh.PullToRefreshBase
import com.ddu.icore.ui.fragment.AbsRVFragment
import com.ddu.routes.RouterProvider
import com.ddu.ui.adapter.StudyRVAdapter
import com.iannotation.model.RouteMeta
import org.jetbrains.anko.support.v4.ctx

/**
 * Created by yzbzz on 2018/1/17.
 */
class StudyFragment : AbsRVFragment<RouteMeta, StudyRVAdapter>() {

    companion object {
        fun newInstance(): StudyFragment {

            val args = Bundle()

            val fragment = StudyFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView() {
        super.initView()
        setTitle("学习")
        mAdapter.setItemClickListener(object : Consumer1<RouteMeta> {
            override fun accept(t: RouteMeta) {
                val bundle = Bundle()
                bundle.putString("title", t.text)
                bundle.putString("bgColor", t.color)
                bundle.putString("url", t.path)
                startFragment(t.cls.name, bundle)
            }
        })
    }

    override fun initData(savedInstanceState: Bundle?) {
        val items = RouterProvider.elements
        for (item in items) {
            mDataEntities.add(RouteMeta.build(item))
        }
        mDataEntities.sort()
    }

    override fun getAdapter(): StudyRVAdapter {
        return StudyRVAdapter(ctx, mDataEntities)
    }

    override fun initRefreshView() {
        mPullToRefreshScrollView.mode = PullToRefreshBase.Mode.DISABLED
    }


}