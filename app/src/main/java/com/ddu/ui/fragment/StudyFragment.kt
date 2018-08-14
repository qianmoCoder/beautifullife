package com.ddu.ui.fragment

import android.os.Bundle
import com.ddu.db.entity.ItemEntity
import com.ddu.icore.refresh.PullToRefreshBase
import com.ddu.icore.ui.fragment.AbstractRecycleViewFragment
import com.ddu.routes.RouterProvider
import com.ddu.ui.adapter.StudyRecycleViewAdapter
import org.jetbrains.anko.support.v4.ctx

/**
 * Created by yzbzz on 2018/1/17.
 */
class StudyFragment : AbstractRecycleViewFragment<ItemEntity, StudyRecycleViewAdapter>(), StudyRecycleViewAdapter.ItemClickListener {

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
        mAdapter.itemClickListener = this
    }

    override fun initData(savedInstanceState: Bundle?) {
        val items = RouterProvider.elements
        for (item in items) {
            val itemEntity = ItemEntity()
            itemEntity.path = item.path
            itemEntity.title = item.text
            itemEntity.color = item.color
            itemEntity.description = item.description
            itemEntity.className = item.cls.name
            mDataEntities.add(itemEntity)
        }
        mDataEntities.sort()
    }

    override fun getAdapter(): StudyRecycleViewAdapter {
        return StudyRecycleViewAdapter(ctx, mDataEntities)
    }

    override fun initRefreshView() {
        mPullToRefreshScrollView.mode = PullToRefreshBase.Mode.DISABLED
    }

    override fun onItemClick(data: ItemEntity) {
        val bundle = Bundle()
        bundle.putString("title", data.title)
        startFragment(data.className, bundle)
    }

}