package com.ddu.icore.dialog

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.ddu.icore.R
import com.ddu.icore.entity.BottomItemEntity
import kotlinx.android.synthetic.main.dialog_bottom_content.*

class DefaultLinearBottomDialogFragment : AbsBottomDialogFragment() {

    private var mShareEntities: List<BottomItemEntity>? = null
    private var mShareAdapter: DefaultLinearBottomAdapter? = null
    private var mCallBack: ((BottomItemEntity?, Int, DefaultLinearBottomDialogFragment) -> Unit)? = null
    private var mTitle = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        rv_share.layoutManager = LinearLayoutManager(context)

        mShareAdapter = DefaultLinearBottomAdapter(context, mShareEntities)
        rv_share.adapter = mShareAdapter

        rv_share.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        mShareAdapter?.setOnItemClickListener(object : DefaultLinearBottomAdapter.OnClickListener<BottomItemEntity> {
            override fun onClick(data: BottomItemEntity?, position: Int) {
                val callBack = data?.cb
                if (null != callBack) {
                    callBack.invoke(data)
                } else {
                    mCallBack?.invoke(data, position, this@DefaultLinearBottomDialogFragment)
                }
            }
        })

        tv_title.text = mTitle
    }

    override fun getLayoutId(): Int = R.layout.dialog_bottom_content

    companion object {

        fun newInstance(title: String = "操作", list: List<BottomItemEntity>, cb: (BottomItemEntity?, Int, DefaultLinearBottomDialogFragment) -> Unit): DefaultLinearBottomDialogFragment {
            return DefaultLinearBottomDialogFragment().apply {
                mTitle = title
                mShareEntities = list
                mCallBack = cb
            }
        }
    }


}
