package com.ddu.ui.fragment.study.ui

import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.ddu.R
import com.ddu.icore.common.ext.ctx
import com.ddu.icore.ui.adapter.common.DefaultRVAdapter
import com.ddu.icore.ui.adapter.common.ViewHolder
import com.ddu.icore.ui.fragment.DefaultFragment
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_study_viewpager2.*

/**
 * Created by yzbzz on 2017/9/1.
 */
@IElement("UI")
class ViewPager2Fragment : DefaultFragment() {

    private var items = arrayListOf<String>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_study_viewpager2
    }

    override fun initView() {

        for (index in 0..5) {
            items.add(index.toString())
        }

        val adapter = object : DefaultRVAdapter<String>(ctx, items) {
            override fun getLayoutId(viewType: Int): Int {
                return R.layout.viewpager2_item_default
            }

            override fun bindView(viewHolder: ViewHolder, data: String, position: Int) {
                with(viewHolder) {
                    setText(R.id.tv_detail, data)
                }
            }
        }

        view_pager2_1.adapter = adapter

        view_pager2_2.adapter = adapter
        view_pager2_2.orientation = ViewPager2.ORIENTATION_VERTICAL

        view_pager2_3.setPageTransformer(MarginPageTransformer(20))
        view_pager2_3.adapter = adapter


//        // 拖拽效果
//        view_pager2_3.beginFakeDrag()
//        if (view_pager2_3.fakeDragBy(-310f)) {
//            view_pager2_3.endFakeDrag()
//        }

    }

}
