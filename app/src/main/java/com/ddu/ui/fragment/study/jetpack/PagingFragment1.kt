package com.ddu.ui.fragment.study.jetpack

import android.annotation.SuppressLint
import android.graphics.Color
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddu.icore.callback.InConsumer1
import com.ddu.icore.common.ext.act
import com.ddu.icore.ui.adapter.common.AbsPagedListAdapter
import com.ddu.icore.ui.adapter.common.ViewHolder
import com.ddu.icore.ui.fragment.DefaultFragment
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_jetpack_paging.*


/**
 * Created by yzbzz on 2019/1/24.
 */
@IElement("Jetpack")
class PagingFragment1 : DefaultFragment() {

    override fun getLayoutId() = com.ddu.R.layout.fragment_jetpack_paging

    override fun initView() {
        val myAdapter = object : AbsPagedListAdapter<DataBean>(act, mDiffCallback) {

            override fun getLayoutId() = android.R.layout.simple_list_item_2

            override fun bindView(viewHolder: ViewHolder, data: DataBean?, position: Int) {
                var text1 = viewHolder.getView<TextView>(android.R.id.text1)
                text1?.setTextColor(Color.RED)
                var text2 = viewHolder.getView<TextView>(android.R.id.text2)
                text2?.setTextColor(Color.BLUE)

                text1?.text = position.toString()
                text2?.setText(data?.data)
            }

            override fun consumer(index: Int, call: InConsumer1<List<DataBean>>) {
                call.accept(loadData(index, 10))
            }

        }

        rv_paging.layoutManager = LinearLayoutManager(act)
        rv_paging.adapter = myAdapter
    }


    inner class DataBean {
        var id: Int = 0
        var data: String = ""
    }

    fun loadData(startPosition: Int, count: Int): List<DataBean> {
        Thread.sleep(2000)
        val list = mutableListOf<DataBean>()
        for (i in startPosition until (startPosition + 10)) {
            val dataBean = DataBean()
            dataBean.id = startPosition + i
            dataBean.data = "测试的内容=${dataBean.id}"
            list.add(dataBean)
        }
        return list
    }


    private val mDiffCallback = object : DiffUtil.ItemCallback<DataBean>() {
        override fun areItemsTheSame(@NonNull oldItem: DataBean, @NonNull newItem: DataBean): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(@NonNull oldItem: DataBean, @NonNull newItem: DataBean): Boolean {
            return oldItem === newItem
        }
    }

}