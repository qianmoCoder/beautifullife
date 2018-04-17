package com.ddu.icore.dialog

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.ddu.icore.R
import com.ddu.icore.entity.BottomItemEntity
import kotlinx.android.synthetic.main.fragment_share.*

class DefaultBottomDialogFragment : AbsBottomDialogFragment() {

    private var mShareEntities: List<BottomItemEntity>? = null
    private var mShareAdapter: DefaultBottomAdapter? = null
    private var mCallBack: ((BottomItemEntity?, Int, DefaultBottomDialogFragment) -> Unit)? = null
    private var mTitle = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var spanCount = SPAN_COUNT
        mShareEntities?.apply {
            spanCount = if (size < SPAN_COUNT) {
                size % SPAN_COUNT
            } else {
                SPAN_COUNT
            }
        }

        val gridLayoutManager = GridLayoutManager(context, spanCount)
        rv_share.layoutManager = gridLayoutManager

        mShareAdapter = DefaultBottomAdapter(context, mShareEntities)
        rv_share.adapter = mShareAdapter

        mShareAdapter?.setOnItemClickListener(object : DefaultBottomAdapter.OnClickListener<BottomItemEntity> {
            override fun onClick(data: BottomItemEntity?, position: Int) {
                val callBack = data?.cb
                if (null != callBack) {
                    callBack.invoke(data)
                } else {
                    mCallBack?.invoke(data, position, this@DefaultBottomDialogFragment)
                }
            }
        })

        tv_title.text = mTitle
    }

    override fun getLayoutId(): Int = R.layout.fragment_share

    companion object {

        private const val SPAN_COUNT = 4

        fun newInstance(title: String = "打开", list: List<BottomItemEntity>, cb: (BottomItemEntity?, Int, DefaultBottomDialogFragment) -> Unit): DefaultBottomDialogFragment {
            return DefaultBottomDialogFragment().apply {
                mTitle = title
                mShareEntities = list
                mCallBack = cb
            }
        }
    }


}
