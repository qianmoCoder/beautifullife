package com.ddu.icore.dialog

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ddu.icore.R
import com.ddu.icore.entity.BottomItemEntity
import kotlinx.android.synthetic.main.i_dialog_bottom_content.*

class DefaultGridBottomDialogFragment : AbsBottomDialogFragment() {

    private var mShareEntities: List<BottomItemEntity>? = null
    private var mShareAdapter: DefaultGridBottomAdapter? = null
    private var mCallBack: ((BottomItemEntity?, Int, DefaultGridBottomDialogFragment) -> Unit)? = null
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


        view.findViewById<RecyclerView>(R.id.rv_share).layoutManager = GridLayoutManager(context, spanCount)

        mShareAdapter = DefaultGridBottomAdapter(context, mShareEntities, SPAN_COUNT)
        view.findViewById<RecyclerView>(R.id.rv_share).adapter = mShareAdapter

        mShareAdapter?.setOnItemClickListener(object : DefaultGridBottomAdapter.OnClickListener<BottomItemEntity> {
            override fun onClick(data: BottomItemEntity?, position: Int) {
                mCallBack?.invoke(data, position, this@DefaultGridBottomDialogFragment)
                data?.cb?.invoke(data)
            }
        })

        tv_title.text = mTitle
    }

    override fun getLayoutId(): Int = R.layout.i_dialog_bottom_content

    companion object {

        private const val SPAN_COUNT = 4

        fun newInstance(title: String = "操作", list: List<BottomItemEntity>, cb: ((BottomItemEntity?, Int, DefaultGridBottomDialogFragment) -> Unit)?): DefaultGridBottomDialogFragment {
            return DefaultGridBottomDialogFragment().apply {
                mTitle = title
                mShareEntities = list
                mCallBack = cb
            }
        }
    }


}
